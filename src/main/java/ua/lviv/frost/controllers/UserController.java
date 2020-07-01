package ua.lviv.frost.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.lviv.frost.dto.AppUserDto;
import ua.lviv.frost.dto.PresentBoxDto;
import ua.lviv.frost.dto.UserResponse;
import ua.lviv.frost.entity.AppUser;
import ua.lviv.frost.entity.enumeration.Role;
import ua.lviv.frost.mapper.AppUserMapper;
import ua.lviv.frost.mapper.UserResponseMapper;
import ua.lviv.frost.security.UserPrincipal;
import ua.lviv.frost.services.PresentBoxService;
import ua.lviv.frost.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final PresentBoxService presentBoxService;
    private final UserService userService;
    private final UserResponseMapper userResponseMapper;
    private final AppUserMapper appUserMapper;

    @GetMapping(path = {"/listAll"})
    public ResponseEntity<List<AppUserDto>> getAllUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.checkIfAdmin(userPrincipal.getId());
        List<AppUser> allUser = userService.getAllUser();
        List<AppUserDto> appUserDto = allUser.stream()
                .map(appUserMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appUserDto);
    }

    @GetMapping(path = {"/allBuyer"})
    public ResponseEntity<List<AppUserDto>> getAllBuyer(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.checkIfAdmin(userPrincipal.getId());
        List<AppUser> allUser = userService.getAllBuyer();
        List<AppUserDto> appUserDto = allUser.stream()
                .map(appUserMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appUserDto);
    }

    @GetMapping(path = {"/allAdmin"})
    public ResponseEntity<List<AppUserDto>> getAllAdmin() {
        List<AppUser> allUser = userService.getAllAdmin();
        List<AppUserDto> appUserDto = allUser.stream()
                .map(appUserMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appUserDto);
    }

    @GetMapping(path = {"/getInfo"})
    public ResponseEntity<AppUserDto> getUserInfo(@RequestParam Integer userId) {

        AppUser user = userService.getUserInfo(userId);
        AppUserDto userDto = appUserMapper.toDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping(path = {"/update"})
    public ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                   @RequestBody UserResponse userResponse) {
        AppUser updatedUser = userService.updateUser(userPrincipal.getId(),
                userResponseMapper.toEntity(userResponse));
        return new ResponseEntity<>(userResponseMapper.toDto(updatedUser), HttpStatus.OK);
    }

    @PutMapping(path = {"/update/role/{id}"})
    public ResponseEntity<AppUserDto> updatePresentBox(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @PathVariable("id") Integer userId,
                                                       @RequestParam String role) {
        userService.checkIfAdmin(userPrincipal.getId());
        AppUser updatedUser = userService.updateUserRole(userId, role);
        return new ResponseEntity<>(appUserMapper.toDto(updatedUser), HttpStatus.OK);
    }

    @DeleteMapping(path = {"/delete/account"})
    public ResponseEntity<PresentBoxDto> deleteAccount(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam Integer userId) {
        HttpStatus status = userService.deleteAccount(userPrincipal.getId(), userId) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status).build();
    }
}
