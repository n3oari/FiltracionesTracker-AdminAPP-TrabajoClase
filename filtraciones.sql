-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-02-2025 a las 23:40:05
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `filtraciones`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facebook`
--

CREATE TABLE `facebook` (
  `usuario` varchar(15) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contraseña` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gmail`
--

CREATE TABLE `gmail` (
  `usuario` varchar(15) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contraseña` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotmail`
--

CREATE TABLE `hotmail` (
  `usuario` varchar(15) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contraseña` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `instagram`
--

CREATE TABLE `instagram` (
  `usuario` varchar(15) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contraseña` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pornhub`
--

CREATE TABLE `pornhub` (
  `usuario` varchar(15) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contraseña` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `twitter`
--

CREATE TABLE `twitter` (
  `usuario` varchar(15) NOT NULL,
  `correo` varchar(30) NOT NULL,
  `contraseña` varchar(20) NOT NULL,
  `telefono` int(9) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `twitter`
--

INSERT INTO `twitter` (`usuario`, `correo`, `contraseña`, `telefono`) VALUES
('admin', 'admin@gmail.com', '123456789', 644793735),
('alex', 'black@gmail.com', '987654321', 659858246),
('andrew', 'steve@gmail.com', '1234', 697731537),
('anthony', 'matthew@gmail.com', '112233', 686822227),
('bill', 'mail@gmail.com', 'homelesspa', 625329983),
('billy', 'jordan@gmail.com', '789456123', 673368723),
('black', 'johnny@gmail.com', 'qwerty123', 674827303),
('bob', 'superman@gmail.com', '12qwaszx', 697338827),
('brandon', 'killer@gmail.com', 'jordan23', 621289458),
('brian', 'jason@gmail.com', 'qwerty1', 655371550),
('buster', 'calvin@gmail.com', 'samsung', 631212844),
('calvin', 'iceman@gmail.com', 'a12345', 634131919),
('cameron', 'harley@gmail.com', 'computer', 602069833),
('captain', 'kevin@gmail.com', 'fuckyou1', 647886633),
('carlos', 'shadow@gmail.com', '7777777', 630478155),
('charles', 'pussy@gmail.com', '121212', 699382391),
('charlie', 'george@gmail.com', 'monkey', 685456441),
('chris', 'robert@gmail.com', '1234567', 683306772),
('coffee', 'sparky@gmail.com', 'michael1', 683075491),
('cowboy', 'captain@gmail.com', 'princess1', 648060072),
('dallas', 'hammer@gmail.com', '123123123', 687235487),
('daniel', 'mark@gmail.com', '1q2w3e4r5t', 620310299),
('darren', 'jeffrey@gmail.com', 'killer', 602151532),
('dave', 'mike@gmail.com', '12345', 602331549),
('david', 'john@gmail.com', '12345678', 617477484),
('dennis', 'anthony@gmail.com', 'qazwsx', 683962108),
('dick', 'sexy@gmail.com', 'shadow', 659008498),
('donald', 'bob@gmail.com', 'asdf', 612753483),
('dragon', 'paul@gmail.com', 'dragon', 668074052),
('edward', 'brian@gmail.com', '1g2w3e4r', 662027042),
('eric', 'cowboy@gmail.com', '999999', 692284778),
('frank', 'mustang@gmail.com', 'iloveyou1', 677588975),
('fred', 'scotty@gmail.com', 'azerty', 638477722),
('freddy', 'jeremy@gmail.com', '777777', 634159164),
('fuck', 'dallas@gmail.com', '12345a', 603664158),
('gary', 'jack@gmail.com', 'princess', 652200162),
('general', 'timothy@gmail.com', 'jordan', 623577833),
('george', 'andrew@gmail.com', 'qwertyuiop', 617130266),
('hammer', '12345@gmail.com', 'fuckyou', 636157709),
('harley', 'justin@gmail.com', 'asdfghjkl', 601577697),
('hunter', 'peter@gmail.com', 'qwe123', 601076016),
('iceman', '123@gmail.com', 'superman', 641339027),
('info', 'info@gmail.com', '123456', 688915355),
('jack', 'natasha@gmail.com', '1q2w3e', 607889159),
('jackson', 'dick@gmail.com', 'jessica', 694633281),
('james', 'charlie@gmail.com', '123456a', 601733248),
('jason', 'hunter@gmail.com', 'target123', 667670273),
('jeff', 'frank@gmail.com', 'daniel', 653261644),
('jeffrey', 'spider@gmail.com', 'monkey1', 679910801),
('jennifer', 'mickey@gmail.com', '888888', 600620177),
('jeremy', 'ranger@gmail.com', 'aaaaaa', 607292257),
('john', 'NULL@gmail.com', '111111', 668946063),
('johnny', '1234@gmail.com', '1q2w3e4r', 632539880),
('jordan', 'webmaster@gmail.com', '1234qwer', 697164432),
('joseph', 'steven@gmail.com', 'gwerty', 680015309),
('joshua', 'eric@gmail.com', 'love', 638496843),
('justin', 'edward@gmail.com', 'gwerty123', 631916971),
('kevin', 'monkey@gmail.com', 'sunshine', 641609052),
('killer', 'tony@gmail.com', 'abcd1234', 672423657),
('little', 'brandon@gmail.com', 'baseball', 660159331),
('mail', 'master@gmail.com', 'myspace1', 624905450),
('mark', 'thomas@gmail.com', 'iloveyou', 608845902),
('martin', 'james@gmail.com', '123321', 643111799),
('master', 'qwerty@gmail.com', '666666', 630104128),
('matt', 'jeff@gmail.com', '11111', 673444043),
('matthew', 'scott@gmail.com', 'zag12wsx', 653572513),
('michael', '2000@gmail.com', 'qwerty', 623513904),
('mickey', 'freddy@gmail.com', 'asdfgh', 621286055),
('mike', 'chris@gmail.com', 'password1', 627050809),
('monkey', 'dennis@gmail.com', 'ashley', 649190849),
('mustang', 'gary@gmail.com', 'michael', 672019726),
('natasha', 'joseph@gmail.com', '555555', 662583701),
('net', 'fred@gmail.com', '88888888', 676132077),
('NULL', 'michael@gmail.com', 'password', 695386238),
('patrick', 'charles@gmail.com', '123qwe', 675450296),
('paul', 'daniel@gmail.com', '123', 683414538),
('peter', 'bill@gmail.com', 'a123456', 647898273),
('philip', 'root@gmail.com', 'FQRG7CS493', 627173822),
('pussy', 'martin@gmail.com', '1qaz2wsx', 608458808),
('qwerty', 'dragon@gmail.com', '654321', 609316653),
('ranger', 'fuck@gmail.com', 'football', 625577089),
('richard', 'dave@gmail.com', '1234567890', 656883789),
('robert', 'david@gmail.com', 'abc123', 653553865),
('root', 'little@gmail.com', 'master', 682985274),
('sales', 'billy@gmail.com', '123654', 689393758),
('scott', 'alex@gmail.com', 'zxcvbnm', 648904714),
('scotty', 'jackson@gmail.com', '123456789a', 697515071),
('sexy', 'matt@gmail.com', 'passer2009', 608849600),
('shadow', 'patrick@gmail.com', '123abc', 640545591),
('snoopy', 'buster@gmail.com', 'soccer', 631202772),
('sparky', 'donald@gmail.com', 'asd123', 600396421),
('spider', 'jennifer@gmail.com', 'football1', 677951542),
('steve', '123456@gmail.com', '000000', 609231899),
('steven', 'tarrant@gmail.com', '222222', 666557181),
('superman', 'joshua@gmail.com', 'love123', 636192280),
('tarrant', 'carlos@gmail.com', 'tinkle', 638913021),
('thomas', 'richard@gmail.com', '123123', 682620724),
('timothy', 'darren@gmail.com', 'charlie', 629288740),
('tony', 'sales@gmail.com', '11111111', 633546927),
('webmaster', 'cameron@gmail.com', '159753', 663458050);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `facebook`
--
ALTER TABLE `facebook`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `gmail`
--
ALTER TABLE `gmail`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `hotmail`
--
ALTER TABLE `hotmail`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `instagram`
--
ALTER TABLE `instagram`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `pornhub`
--
ALTER TABLE `pornhub`
  ADD PRIMARY KEY (`usuario`);

--
-- Indices de la tabla `twitter`
--
ALTER TABLE `twitter`
  ADD PRIMARY KEY (`usuario`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
