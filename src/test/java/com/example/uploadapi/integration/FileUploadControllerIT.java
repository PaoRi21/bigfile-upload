package com.example.uploadapi.integration;

import com.example.uploadapi.config.TestAwsConfig;
import com.example.uploadapi.unit.S3StorageService;
import com.example.uploadapi.util.TestJwtTokens;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import software.amazon.awssdk.services.s3.S3Client;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestAwsConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileUploadControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private S3StorageService s3StorageService;

    @MockBean
    private S3Client s3Client;

    @BeforeEach
    void setup() {
        // Stub: cualquier llamada a uploadFile devuelve una URL simulada
        when(s3StorageService.uploadFile(any()))
                .thenReturn("https://s3.amazonaws.com/bigfile-upload-paori21/test.txt");
    }

    // Agregar prueba de integración para el controller FileUploadController un test ok
    @Test
    void subeArchivoTestOk() throws Exception {
        MockMultipartFile archivo = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "hola mundo".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/file/upload")
                        .file(archivo)
                        .header("Authorization", "Bearer " + TestJwtTokens.validToken())
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        containsString("https://s3.amazonaws.com/bigfile-upload-paori21/")));

        verify(s3StorageService).uploadFile(any());
    }


    @Test
    void subeArchivoTestForbidden() throws Exception {
        MockMultipartFile archivo = new MockMultipartFile(
                "file",
                "test.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "hola mundo".getBytes()
        );

        mockMvc.perform(multipart("/api/v1/file/upload")
                        .file(archivo)
                        .header("Authorization", "Bearer " + TestJwtTokens.invalidToken())
                )
                .andExpect(status().is4xxClientError());

    }

    // Copilot: genera una prueba que verifique que un archivo con extensión .exe sea rechazado con 415
    // Copilot: genera una prueba que verifique que un archivo >1GB devuelva 413 Request Entity Too Large
}
