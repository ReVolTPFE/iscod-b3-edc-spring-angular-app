package com.asteiner.edc.Controller;

import com.asteiner.edc.Entity.User;
import com.asteiner.edc.Others.GetUserDto;
import com.asteiner.edc.Service.UserService;
import com.asteiner.edc.swaggerDocsDtos.RegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @Operation(summary = "User register", description = "Create and authenticates a user and returns its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully registered", content = {
                    @Content(mediaType = "application/json", schema = @Schema(type = "integer", description = "User id", example = "1"))
            })
    })
    @PostMapping("/create")
    @ResponseStatus(code = HttpStatus.CREATED)
    public int createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User register details", required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterRequest.class)))
            @RequestBody User user) {
        return userService.create(user);
    }

    @Operation(summary = "Get users", description = "Gets all users registered in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users successfully retrieved", content = {
                    @Content(mediaType = "application/json", schema = @Schema(
                            type = "array",
                            implementation = User.class),
                            examples = @ExampleObject(value = """
                                    [
                                            {
                                                    "username": "Username1",
                                                    "email": "user1@example.com",
                                                    "id": 1
                                            },
                                            {
                                                    "username": "Username2",
                                                    "email": "user2@example.com",
                                                    "id": 2
                                            }
                                    ]
                            """))
            })
    })
    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<GetUserDto> getUsers() {
        return userService.getUsers();
    }
}
