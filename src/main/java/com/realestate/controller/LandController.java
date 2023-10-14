package com.realestate.controller;

import com.realestate.dto.LandPropertyDto;
import com.realestate.service.LandService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/land")
public class LandController {

    private final LandService landService;

    public LandController(LandService landService) {
        this.landService = landService;
    }

    @PostMapping
    public ResponseEntity<LandPropertyDto> saveLandProperty(@Valid @RequestBody LandPropertyDto dto){
        LandPropertyDto saved = landService.saveLand(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LandPropertyDto> getLandById(@PathVariable Long id){
        return landService.getLandById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<LandPropertyDto>> getAllLands(){
        List<LandPropertyDto> allLands = landService.getAllLands();
        if (allLands.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(allLands);
    }
}
