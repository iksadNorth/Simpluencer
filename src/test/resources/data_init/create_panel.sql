INSERT INTO agent (id, email, password, nickname) VALUES
(100, 'mock email1', '1234', 'user1'),
(200, 'mock email2', '1234', 'user2');

INSERT INTO panel (id, agent_id, provider, principal_name, description, location) VALUES
(100, 100, 'google', 'rfewdr3w', 'mock description1', 0),
(200, 100, 'facebook', 'rfewdr3w', 'mock description2', 1),
(300, 100, 'google', '7kdi8wj2', 'mock description3', 2),

(400, 200, 'google', '4543wer54ef', 'mock description4', 0),
(500, 200, 'facebook', '4543wer54ef', 'mock description5', 1),
(600, 200, 'google', 'hhted44', 'mock description6', 2),
(700, 200, 'facebook', 'hhted44', 'mock description7', 3),
(800, 200, 'github', 'hhted44', 'mock description8', 1);