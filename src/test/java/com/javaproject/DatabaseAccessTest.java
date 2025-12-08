package com.javaproject;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseAccessTest {

    @Mock
    private NamedParameterJdbcTemplate jdbc;

    @InjectMocks
    private DatabaseAccess databaseAccess;

    public DatabaseAccessTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuthorities() {
        List<String> mockAuthorities = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

        when(jdbc.queryForList(anyString(), any(MapSqlParameterSource.class), eq(String.class)))
                .thenReturn(mockAuthorities);

        List<String> authorities = databaseAccess.getAuthorities();

        assertNotNull(authorities);
        assertEquals(2, authorities.size());
        assertTrue(authorities.contains("ROLE_USER"));
        assertTrue(authorities.contains("ROLE_ADMIN"));

        verify(jdbc, times(1))
                .queryForList(anyString(), any(MapSqlParameterSource.class), eq(String.class));
    }

    @Test
    void testGetBoardGames() {
        // Arrange
        List<BoardGame> mockBoardGames = Arrays.asList(
                new BoardGame(),
                new BoardGame()
        );
        BeanPropertyRowMapper<BoardGame> mapper = new BeanPropertyRowMapper<>(BoardGame.class);

        when(jdbc.query(eq("SELECT * FROM boardgames"), eq(mapper)))
                .thenReturn(mockBoardGames);

        // Act
        List<BoardGame> result = databaseAccess.getBoardGames();

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void testGetBoardGames_EmptyList() {
        // Arrange
        BeanPropertyRowMapper<BoardGame> mapper = new BeanPropertyRowMapper<>(BoardGame.class);
        when(jdbc.query(eq("SELECT * FROM boardgames"), eq(mapper)))
                .thenReturn(Collections.emptyList());

        // Act
        List<BoardGame> result = databaseAccess.getBoardGames();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetBoardGame_ExistingId() {
        // Arrange
        Long testId = 1L;
        BoardGame mockBoardGame = new BoardGame();
        List<BoardGame> mockList = Arrays.asList(mockBoardGame);

        MapSqlParameterSource params = new MapSqlParameterSource("id", testId);
        BeanPropertyRowMapper<BoardGame> mapper = new BeanPropertyRowMapper<>(BoardGame.class);

        when(jdbc.query(eq("SELECT * FROM boardgames WHERE id = :id"), eq(params), eq(mapper)))
                .thenReturn(mockList);

        // Act
        BoardGame result = databaseAccess.getBoardGame(testId);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetBoardGame_NonExistingId() {
        // Arrange
        Long testId = 999L;
        MapSqlParameterSource params = new MapSqlParameterSource("id", testId);
        BeanPropertyRowMapper<BoardGame> mapper = new BeanPropertyRowMapper<>(BoardGame.class);

        when(jdbc.query(eq("SELECT * FROM boardgames WHERE id = :id"), eq(params), eq(mapper)))
                .thenReturn(Collections.emptyList());

        // Act
        BoardGame result = databaseAccess.getBoardGame(testId);

        // Assert
        assertNull(result);
    }

    @Test
    void testAddReview() {
        Review review = new Review();
        review.setGameId(1L);
        review.setText("Awesome game!");

        // Mock the update method to return 1 row affected
        when(jdbc.update(anyString(), any(MapSqlParameterSource.class))).thenReturn(1);

        int result = databaseAccess.addReview(review);

        assertEquals(1, result);

        verify(jdbc).update(anyString(), any(MapSqlParameterSource.class));
    }

}
