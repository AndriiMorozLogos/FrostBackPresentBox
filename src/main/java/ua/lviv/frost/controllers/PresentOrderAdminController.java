package ua.lviv.frost.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.lviv.frost.dto.PresentOrderDto;
import ua.lviv.frost.entity.PresentOrder;
import ua.lviv.frost.entity.enumeration.OrderStatus;
import ua.lviv.frost.mapper.PresentOrderMapper;
import ua.lviv.frost.security.UserPrincipal;
import ua.lviv.frost.services.PresentBoxService;
import ua.lviv.frost.services.PresentOrderAdminService;
import ua.lviv.frost.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/present/order/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PresentOrderAdminController {

    private final UserService userService;
    private final PresentOrderAdminService presentOrderAdminService;
    private final PresentOrderMapper presentOrderMapper;

    @GetMapping
    public ResponseEntity<List<PresentOrderDto>> getAllPresentOrder(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.checkIfAdmin(userPrincipal.getId());
        List<PresentOrder> allPresentBox = presentOrderAdminService.getAllPresentBox();
        List<PresentOrderDto> allPresentBoxDto = allPresentBox.stream()
                .map(presentOrderMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allPresentBoxDto);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<PresentOrderDto> getPresentOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                           @PathVariable("id") Integer presentOrderId) {
        userService.checkIfAdmin(userPrincipal.getId());
        PresentOrder presentOrder = presentOrderAdminService.getPresentBox(presentOrderId);
        PresentOrderDto boxDto = presentOrderMapper.toDto(presentOrder);
        return new ResponseEntity<>(boxDto, HttpStatus.OK);
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<PresentOrderDto> updatePresentOrderStatus(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                    @PathVariable("id") Integer presentBoxId,
                                                                    @RequestParam String orderStatus) {
        userService.checkIfAdmin(userPrincipal.getId());
        PresentOrder updateOrderStatus = presentOrderAdminService.updateOrderStatus(presentBoxId, orderStatus);
        return (presentBoxId == null
                ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                : new ResponseEntity<>(presentOrderMapper.toDto(updateOrderStatus), HttpStatus.OK));
    }

}
