INSERT INTO COMMAND(id, command_text, command_type, required_authorization) values (0, 'ping', 'BASIC', 'BASIC')
INSERT INTO BASIC_COMMAND(id, command_id, response) values (0, 0, 'pong')

INSERT INTO DIALOG(id, text, link_text) values (0, 'Hold it stranger, state your business.', '')

INSERT INTO DIALOG(id, text, link_text) values (1, 'Ah! I see, you are just visiting town.', 'Just visiting!')
INSERT INTO DIALOG(id, text, link_text) values (2, 'INSOLENCE, Silence before I cut your tongue', 'Draw your sword')
INSERT INTO DIALOG(id, text, link_text) values (3, 'You silently enter town...', 'Make your way past the guard')

INSERT INTO DIALOG_FOLLOW_UP(dialog_id, follow_up_id, follow_up_key) values (0, 1, '🏠')
INSERT INTO DIALOG_FOLLOW_UP(dialog_id, follow_up_id, follow_up_key) values (0, 2, '⚔')

INSERT INTO DIALOG_FOLLOW_UP(dialog_id, follow_up_id, follow_up_key) values (1, 3, '🛤️')