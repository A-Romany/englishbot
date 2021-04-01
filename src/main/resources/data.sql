-- insert default values to db
INSERT INTO PUBLIC.WORD(ID, ENGLISH_VALUE, UKRAINIAN_VALUE)
    SELECT * FROM (VALUES
         (1, 'black', 'чорний'),
         (2, 'grey', 'сірий'),
         (3, 'green', 'зелений'),
         (4, 'white', 'білий'),
         (5, 'brown', 'коричневий'),
         (6, 'blue', 'синій'),
         (7, 'red', 'червоний'),
         (8, 'yellow', 'жовтий'),
         (9, 'pink', 'рожевий'),
         (10, 'orange', 'жовтогарячий'),
         (11, 'purple', 'фіолетовий'),
         (12, 'beige', 'бежевий'),
         (13, 'gold', 'золотий')) as DEFAULT_VALUES
    WHERE NOT EXISTS (SELECT * FROM PUBLIC.WORD);
