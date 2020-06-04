INSERT INTO COMMAND(id, command_text, description, usage, command_type, required_authorization) values (0, 'test', 'This is an example command', '~test', 'BASIC', 'BASIC')
INSERT INTO BASIC_COMMAND(id, command_id, response) values (0, 0, 'Heard ya loud and clear :D')
INSERT INTO COMMAND(id, command_text, description, usage, command_type, required_authorization) values (1, 'testembed', 'This is an example embed command!', '~testembed', 'EMBED', 'BASIC')
INSERT INTO EMBED_COMMAND(id, command_id, title, description, color) values (0, 1, 'testembed', 'embed description go here poggers', null)