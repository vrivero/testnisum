package com.test.users.controllers;

import com.test.users.dtos.request.LoginRequest;
import com.test.users.dtos.request.UserRequest;
import com.test.users.dtos.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@Tag(name = "Users", description = "Api de Registro de Usuarios")
public interface UserApi {


    @Operation(
            summary = "Listado de Usuarios",
            description = "Retorna el listado de los usuarios almacenados en la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success", useReturnTypeSchema = true,
                    content = @Content(examples = @ExampleObject(
                            value = "{\n" +
                                    "    \"message\": \"success\",\n" +
                                    "    \"data\": {\n" +
                                    "        \"totalElem\": 3,\n" +
                                    "        \"size\": 20,\n" +
                                    "        \"totalPage\": 1,\n" +
                                    "        \"page\": 0,\n" +
                                    "        \"users\": [\n" +
                                    "            {\n" +
                                    "                \"id\": \"2fb2103c-4237-4d6e-9c9e-96d14d995855\",\n" +
                                    "                \"name\": \"Victor Rodriguez Perez\",\n" +
                                    "                \"email\": \"victor.perez@rodriguez.com\",\n" +
                                    "                \"created\": \"2024-02-22T17:43:01.638+00:00\",\n" +
                                    "                \"updated\": \"2024-02-22T17:44:49.097+00:00\",\n" +
                                    "                \"last_login\": \"2024-02-22T17:43:01.638+00:00\",\n" +
                                    "                \"token\": null,\n" +
                                    "                \"isactive\": true,\n" +
                                    "                \"phones\": null\n" +
                                    "            },\n" +
                                    "            {\n" +
                                    "                \"id\": \"1428ca02-fb79-4c8c-ae3d-5a63a4e30dc7\",\n" +
                                    "                \"name\": \"Juan Rodriguez\",\n" +
                                    "                \"email\": \"juan@rodriguez.com\",\n" +
                                    "                \"created\": \"2024-02-22T17:43:20.337+00:00\",\n" +
                                    "                \"updated\": \"2024-02-22T17:43:20.337+00:00\",\n" +
                                    "                \"last_login\": \"2024-02-22T17:43:20.337+00:00\",\n" +
                                    "                \"token\": null,\n" +
                                    "                \"isactive\": true,\n" +
                                    "                \"phones\": null\n" +
                                    "            },\n" +
                                    "            {\n" +
                                    "                \"id\": \"5715e105-30a4-4be8-a2bf-c440b97dc47d\",\n" +
                                    "                \"name\": \"Juan Rodriguez\",\n" +
                                    "                \"email\": \"juan.rod@rodriguez.com\",\n" +
                                    "                \"created\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                                    "                \"updated\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                                    "                \"last_login\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                                    "                \"token\": null,\n" +
                                    "                \"isactive\": true,\n" +
                                    "                \"phones\": null\n" +
                                    "            }\n" +
                                    "        ]\n" +
                                    "    }\n" +
                                    "}"
                    )))
    })
    ResponseEntity<MessageResponse> listAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection,
            @RequestParam(required = false) String filter
    );

    @Operation(
            summary = "Registro de nuevo Usuario",
            description = "Permite crear nuevo usuario dentro de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente", content = @Content(examples = @ExampleObject(
                    value = "{\n" +
                            "    \"message\": \"Usuario creado exitosamente\",\n" +
                            "    \"data\": {\n" +
                            "        \"id\": \"5715e105-30a4-4be8-a2bf-c440b97dc47d\",\n" +
                            "        \"name\": \"Juan Rodriguez\",\n" +
                            "        \"email\": \"juan.rod@rodriguez.com\",\n" +
                            "        \"created\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                            "        \"updated\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                            "        \"last_login\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                            "        \"token\": null,\n" +
                            "        \"isactive\": true,\n" +
                            "        \"phones\": [\n" +
                            "            {\n" +
                            "                \"id\": \"92487429-b765-4a9e-9ab7-ecb347d7e124\",\n" +
                            "                \"number\": \"1234567\",\n" +
                            "                \"citycode\": \"1\",\n" +
                            "                \"countrycode\": \"57\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"id\": \"3efe4a66-7d78-4d8e-9705-027e94dca78f\",\n" +
                            "                \"number\": \"654879\",\n" +
                            "                \"citycode\": \"2\",\n" +
                            "                \"countrycode\": \"57\"\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}"
            ))),
            @ApiResponse(responseCode = "400", description = "Error en el formato de los datos recibidos", content = @Content(examples = @ExampleObject(
                    value = "{\"message\": \"error: formato incorrecto\"}"
            ))),
            @ApiResponse(responseCode = "409", description = "Correo ya se encuentra registrado", content = @Content(examples = @ExampleObject(
                    value = "{\"message\": \"error: Correo ya se encuentra registrado\"}"
            )))
    })
    ResponseEntity<MessageResponse> create(@RequestBody UserRequest request);


    @Operation(
            summary = "Actualización de datos del Usuario",
            description = "Permite actualizar un usuario existente dentro de la BD")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario actualizado exitosamente", content = @Content(examples = @ExampleObject(
                    value = "{\n" +
                            "    \"message\": \"Usuario actualizado exitosamente\",\n" +
                            "    \"data\": {\n" +
                            "        \"id\": \"5715e105-30a4-4be8-a2bf-c440b97dc47d\",\n" +
                            "        \"name\": \"Juan Rodriguez\",\n" +
                            "        \"email\": \"juan.rod@rodriguez.com\",\n" +
                            "        \"created\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                            "        \"updated\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                            "        \"last_login\": \"2024-02-22T17:44:58.691+00:00\",\n" +
                            "        \"token\": null,\n" +
                            "        \"isactive\": true,\n" +
                            "        \"phones\": [\n" +
                            "            {\n" +
                            "                \"id\": \"92487429-b765-4a9e-9ab7-ecb347d7e124\",\n" +
                            "                \"number\": \"1234567\",\n" +
                            "                \"citycode\": \"1\",\n" +
                            "                \"countrycode\": \"57\"\n" +
                            "            },\n" +
                            "            {\n" +
                            "                \"id\": \"3efe4a66-7d78-4d8e-9705-027e94dca78f\",\n" +
                            "                \"number\": \"654879\",\n" +
                            "                \"citycode\": \"2\",\n" +
                            "                \"countrycode\": \"57\"\n" +
                            "            }\n" +
                            "        ]\n" +
                            "    }\n" +
                            "}"
            ))),
            @ApiResponse(responseCode = "400", description = "Error en el formato de los datos recibidos",
                    content = @Content(examples = @ExampleObject( value = "{\"message\": \"error: formato incorrecto\"}"
            ))),
            @ApiResponse(responseCode = "404", description = "ID del usuario no se encontró", content = @Content(examples = @ExampleObject(
                    value = "{\"message\": \"error: Usuario no encontrado\"}"
            ))),
            @ApiResponse(responseCode = "409", description = "Correo ya se encuentra registrado", content = @Content(examples = @ExampleObject(
                    value = "{\"message\": \"error: Correo ya se encuentra registrado\"}"
            )))
    })
    ResponseEntity<MessageResponse> update(@PathVariable String id, @Valid @RequestBody UserRequest request);

    @Operation(
            summary = "Autenticación de Usuario",
            description = "Permite obtener token de sesión de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario autenticado exitosamente", content = @Content(examples = @ExampleObject(
                    value = "{\n" +
                            "        \"message\": \"Usuario creado exitosamente\",\n" +
                            "        \"data\": {\n" +
                            "        \"id\": \"fa9c2b9a-fe0a-4b5e-913a-3cf2014b15fc\",\n" +
                            "        \"name\": \"Juan Rodriguez Perez\",\n" +
                            "        \"email\": \"juan.edited@rodriguez.com\",\n" +
                            "        \"created\": \"2024-02-22T17:11:16.610+00:00\",\n" +
                            "        \"updated\": \"2024-02-22T17:13:31.752+00:00\",\n" +
                            "        \"last_login\": \"2024-02-22T17:13:31.736+00:00\",\n" +
                            "        \"token\": \"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuLmVkaXRlZEByb2RyaWd1ZXouY29tIiwiaWF0IjoxNzA4NjIyMDExLCJleHAiOjE3MDg2MjI5MTF9.oONUv8yNzm8ntsxpB0WCMZDdLT8B6L7uOFxZ838agJ4wlG6svHyJUWDzH1zBgRzHhw-Hx0TFvBCTBfaCq4wkjg\",\n" +
                            "        \"isactive\": true,\n" +
                            "        \"phones\": null\n" +
                            "        }\n" +
                            "        }"
            ))),
            @ApiResponse(responseCode = "400", description = "Error en el formato de los datos recibidos",content = @Content(examples = @ExampleObject(
                    value = "{\"message\": \"error: Credenciales incorrectas\"}"
            ))),
            @ApiResponse(responseCode = "401", description = "No autorizado",content = @Content(examples = @ExampleObject(
                    value = "{\"message\": \"error: Usuario se encuentra desactivado\"}"
            )))
    })
    ResponseEntity<MessageResponse> login(@RequestBody LoginRequest request);


}
