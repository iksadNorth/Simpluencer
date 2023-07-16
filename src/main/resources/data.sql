INSERT INTO agent (id, email, password, nickname) VALUES
(1000, 'iksadnorth@gmail.com', '1234', 'iksadnorth');

INSERT INTO panel (id, agent_id, provider, principal_name, description, account, location) VALUES
(1000, 1000, 'GOOGLE', '23jrifjdk34', 'mock description1', 'youtube.com', 0),
(2000, 1000, 'GOOGLE', 'GHT4REFRG4', 'mock description2', 'facebook.com', 1),
(3000, 1000, 'GOOGLE', '4rfd3gnbr', 'mock description3', 'instagram.com', 2);

INSERT INTO notice (id, writer_id, content, image_uri) VALUES
(1000, 1000, 'notice 1', 'simpluencer_logo-removebg-favicon.png'),
(2000, 1000, 'notice 2', 'simpluencer_logo-removebg-favicon.png'),
(3000, 1000, 'notice 3', 'simpluencer_logo-removebg-favicon.png'),
(4000, 1000, 'notice 4', 'simpluencer_logo-removebg-favicon.png'),
(5000, 1000, 'notice 5', 'simpluencer_logo-removebg-favicon.png'),
(6000, 1000, 'notice 6', 'simpluencer_logo-removebg-favicon.png'),
(7000, 1000, 'notice 7', 'simpluencer_logo-removebg-favicon.png'),
(8000, 1000, 'notice 8', 'simpluencer_logo-removebg-favicon.png'),
(9000, 1000, 'notice 9', 'simpluencer_logo-removebg-favicon.png'),
(10000, 1000, 'notice 10', 'simpluencer_logo-removebg-favicon.png');
