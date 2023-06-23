INSERT INTO agent (email, password, nickname) VALUES
('iksadnorth@gmail.com', '1234', 'iksadnorth');

INSERT INTO panel (agent_id, provider, principal_name, description, email, location) VALUES
(1, 'GOOGLE', '23jrifjdk34', 'mock description1', 'youtube.com', 0),
(1, 'GOOGLE', 'GHT4REFRG4', 'mock description2', 'facebook.com', 1),
(1, 'GOOGLE', '4rfd3gnbr', 'mock description3', 'instagram.com', 2);