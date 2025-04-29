-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Апр 29 2025 г., 11:52
-- Версия сервера: 10.4.24-MariaDB
-- Версия PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `hotel`
--

-- --------------------------------------------------------

--
-- Структура таблицы `auth_data`
--

CREATE TABLE `auth_data` (
  `id` int(11) NOT NULL,
  `login` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `auth_data`
--

INSERT INTO `auth_data` (`id`, `login`, `password`) VALUES
(1, 'Login', 'notQWE0RTY12345');

-- --------------------------------------------------------

--
-- Структура таблицы `guest`
--

CREATE TABLE `guest` (
  `id` int(11) NOT NULL,
  `surname` varchar(30) NOT NULL,
  `name` varchar(30) NOT NULL,
  `patronymic` varchar(30) NOT NULL,
  `passport` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `guest`
--

INSERT INTO `guest` (`id`, `surname`, `name`, `patronymic`, `passport`) VALUES
(1, 'Лермонтов', 'Михаил', 'Юрьевич', '1111-222222'),
(2, 'Тургенев', 'Иван', 'Сергеевич', '1221-224452'),
(3, 'Пушкин', 'Александр', 'Сергеевич', '1122-177332'),
(8, 'Толстой', 'Лев', 'Николаевич', '8819-413523'),
(9, 'Пешков', 'Алексей', 'Максимович', '2153-789101');

-- --------------------------------------------------------

--
-- Структура таблицы `guestroomassignments`
--

CREATE TABLE `guestroomassignments` (
  `id` int(11) NOT NULL,
  `id_room` int(11) NOT NULL,
  `id_guest` int(11) NOT NULL,
  `date_start` date NOT NULL,
  `date_finish` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `guestroomassignments`
--

INSERT INTO `guestroomassignments` (`id`, `id_room`, `id_guest`, `date_start`, `date_finish`) VALUES
(6, 21, 9, '2025-04-30', '2025-05-03'),
(7, 34, 2, '2025-04-29', '2025-05-10');

-- --------------------------------------------------------

--
-- Структура таблицы `room`
--

CREATE TABLE `room` (
  `id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `type` varchar(20) NOT NULL,
  `occupancy` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Дамп данных таблицы `room`
--

INSERT INTO `room` (`id`, `number`, `type`, `occupancy`) VALUES
(20, 101, 'Эконом', 1),
(21, 102, 'Эконом', 2),
(22, 103, 'Эконом', 3),
(23, 104, 'Эконом', 4),
(24, 201, 'Стандарт', 1),
(25, 202, 'Стандарт', 2),
(26, 203, 'Стандарт', 3),
(27, 204, 'Стандарт', 4),
(28, 301, 'Делюкс', 1),
(29, 302, 'Делюкс', 2),
(30, 303, 'Делюкс', 3),
(31, 304, 'Делюкс', 4),
(32, 401, 'Люкс', 1),
(33, 402, 'Люкс', 2),
(34, 403, 'Люкс', 3),
(35, 404, 'Люкс', 4);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `auth_data`
--
ALTER TABLE `auth_data`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `guest`
--
ALTER TABLE `guest`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `guestroomassignments`
--
ALTER TABLE `guestroomassignments`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `auth_data`
--
ALTER TABLE `auth_data`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `guest`
--
ALTER TABLE `guest`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `guestroomassignments`
--
ALTER TABLE `guestroomassignments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT для таблицы `room`
--
ALTER TABLE `room`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
