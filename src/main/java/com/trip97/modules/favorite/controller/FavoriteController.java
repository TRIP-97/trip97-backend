package com.trip97.modules.favorite.controller;

import com.trip97.modules.favorite.model.Favorite;
import com.trip97.modules.favorite.model.FavoriteListDto;
import com.trip97.modules.favorite.model.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<?> selectFavorites(@RequestParam Map<String, String> map) {
        System.out.println("검색어는?"+map.get("word"));
        FavoriteListDto favoriteList = favoriteService.getFavorites(map);
        if (favoriteList != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(headers).body(favoriteList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getFavoritesByMemberId(Integer memberId) {
        List<Favorite> list = favoriteService.getFavoritesByMemberId(memberId);
        if (list != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(headers).body(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/attraction")
    public ResponseEntity<?> selectFavorite(@RequestParam("attractionId") Integer attractionId,
                                            @RequestParam("memberId") Integer memberId) {

        Favorite favorite = new Favorite();
        favorite.setAttractionId(attractionId);
        favorite.setMemberId(memberId);

        Favorite result = favoriteService.getFavorite(favorite);

        if (result != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
            return ResponseEntity.ok().headers(headers).body(favorite);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> registerFavorite(@RequestBody Favorite favorite) {

        Favorite result = favoriteService.getFavorite(favorite);

        if (result != null) {
            return ResponseEntity.noContent().build();
        }else {
            favoriteService.registerFavorite(favorite);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<?> removeFavorite(@RequestParam("attractionId") Integer attractionId,
                                            @RequestParam("memberId") Integer memberId) {

        Favorite favorite = new Favorite();
        favorite.setAttractionId(attractionId);
        favorite.setMemberId(memberId);

        favoriteService.removeFavorite(favorite);
        return ResponseEntity.noContent().build();
    }
}
