INSERT INTO authorities (authority) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO boardgames (name, level, minPlayers, maxPlayers, gameType) VALUES
('Catan', 'Medium', 3, 4, 'Board'),
('Ticket to Ride', 'Easy', 2, 5, 'Board');
INSERT INTO reviews (gameId, text) VALUES
(1, 'Great game!'),
(1, 'Fun for family');