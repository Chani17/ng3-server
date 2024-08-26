package com.nggg.ng3.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.nggg.ng3.entity.Image;
import com.nggg.ng3.entity.User;
import com.nggg.ng3.model.SaveImageDTO;
import com.nggg.ng3.model.UploadImageDTO;
import com.nggg.ng3.repository.ImageRepository;
import com.nggg.ng3.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class GameService {

	private final ImageRepository imageRepository;
	private final UserRepository userRepository;

	@Value("${spring.cloud.gcp.storage.bucket}")
	private String bucketName;

	public void saveImage(SaveImageDTO saveImageDTO) {
		User user = userRepository.findById(saveImageDTO.getEmail())
			.orElseThrow(() -> new IllegalStateException("로그인 접속을 확인해주세요."));

		byte[] byteImage = convertMultipartFileToByteArray(saveImageDTO.getImage());
		UploadImageDTO response = uploadImage(user, saveImageDTO.getWord(), byteImage);

		Image image = Image.builder()
			.title(response.getTitle())
			.url(response.getImageUrl())
			.user(user)
			.build();

		imageRepository.save(image);
	}

	private byte[] convertMultipartFileToByteArray(MultipartFile file) {
		try {
			return file.getBytes();
		} catch (IOException e) {
			throw new RuntimeException("파일을 byte 배열로 변환하는 데 실패했습니다.", e);
		}
	}

	public UploadImageDTO uploadImage(User user, String word, byte[] image) {
		// init gcp storage client
		Storage storage = StorageOptions.getDefaultInstance().getService();

		// 이미지 이름 format : {nickname}-{word}-{uuid}
		String title = user.getNickname() + "-" + word + "-" + UUID.randomUUID();

		try {
			BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, title)
				.setContentType("image/png")
				.build();

			// upload image in cloud
			Blob uploadImage = storage.createFrom(blobInfo, new ByteArrayInputStream(image));

			// return image url that uploaded
			String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + title;

			return UploadImageDTO.builder()
				.title(title)
				.imageUrl(imageUrl)
				.build();

		} catch (IOException e) {
			throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
		}
	}

}
