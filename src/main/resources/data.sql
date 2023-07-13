INSERT INTO agent (id, email, password, nickname) VALUES
(1000, 'iksadnorth@gmail.com', '1234', 'iksadnorth');

INSERT INTO panel (id, agent_id, provider, principal_name, description, account, location) VALUES
(1000, 1000, 'GOOGLE', '23jrifjdk34', 'mock description1', 'youtube.com', 0),
(2000, 1000, 'GOOGLE', 'GHT4REFRG4', 'mock description2', 'facebook.com', 1),
(3000, 1000, 'GOOGLE', '4rfd3gnbr', 'mock description3', 'instagram.com', 2);