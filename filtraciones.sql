-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-02-2025 a las 02:27:11
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
-- Estructura de tabla para la tabla `administradores`
--

CREATE TABLE `administradores` (
  `id_administrador` int(11) NOT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `contraseña` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `administradores`
--

INSERT INTO `administradores` (`id_administrador`, `usuario`, `contraseña`) VALUES
(5, 'mario', 'loldam132'),
(9, 'root', 'sudo666');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `credenciales`
--

CREATE TABLE `credenciales` (
  `id_usuario` int(6) NOT NULL,
  `usuario` varchar(20) DEFAULT NULL,
  `correo` varchar(30) DEFAULT NULL,
  `contraseña` varchar(30) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  `id_filtracion` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `filtraciones`
--

CREATE TABLE `filtraciones` (
  `id_filtracion` int(6) NOT NULL,
  `plataforma` varchar(20) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `numero_afectados` int(11) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `medidas` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `filtraciones`
--

INSERT INTO `filtraciones` (`id_filtracion`, `plataforma`, `fecha`, `numero_afectados`, `descripcion`, `medidas`) VALUES
(23, 'dd', '2000-10-10', 49581, 'ddd', 'huir al bosque'),
(99, 'Telegram', '1990-10-10', 49581, 'putos rusos loko', 'dd'),
(100, 'Twitter', '2020-01-01', 49581, 'putos rusos loko', 'nada'),
(233, 'Twitter', '2020-01-01', 49581, 'putos rusos lojko', 'rezar'),
(555, 'twitter', '2000-10-10', 49581, 'skdjskd', 'sdjskd');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `id_historial` int(11) NOT NULL,
  `fecha` datetime DEFAULT current_timestamp(),
  `cambios` varchar(300) DEFAULT NULL,
  `id_administrador` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `historial`
--

INSERT INTO `historial` (`id_historial`, `fecha`, `cambios`, `id_administrador`) VALUES
(2, '2025-02-19 22:40:23', 'hhchha', 9),
(3, '2025-02-19 22:43:45', 'sdsdsd', 9),
(4, '2025-02-19 22:44:27', 'sdksldlk', 9),
(5, '2025-02-19 22:47:41', 'sdsdsd', 9),
(6, '2025-02-19 22:53:36', '[-]sds [+] Twitter', 9),
(7, '2025-02-19 22:57:43', NULL, 9),
(8, '2025-02-19 22:58:37', '[*]descripcion [-]putos rusos loko [+]putos chinos loko', 9),
(9, '2025-02-20 00:06:07', '[*]plataforma [-]Twitter[+]Telegram', 9),
(10, '2025-02-20 02:12:03', '[*]descripcion [-]putos chinos loko [+] putos rusos loko', 9);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administradores`
--
ALTER TABLE `administradores`
  ADD PRIMARY KEY (`id_administrador`);

--
-- Indices de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD PRIMARY KEY (`id_usuario`),
  ADD KEY `fk_id_filtration` (`id_filtracion`);

--
-- Indices de la tabla `filtraciones`
--
ALTER TABLE `filtraciones`
  ADD PRIMARY KEY (`id_filtracion`);

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`id_historial`),
  ADD KEY `fk_id_administrador` (`id_administrador`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `credenciales`
--
ALTER TABLE `credenciales`
  MODIFY `id_usuario` int(6) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `id_historial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `credenciales`
--
ALTER TABLE `credenciales`
  ADD CONSTRAINT `fk_id_filtracion` FOREIGN KEY (`id_filtracion`) REFERENCES `filtraciones` (`id_filtracion`) ON DELETE CASCADE;

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `fk_id_administrador` FOREIGN KEY (`id_administrador`) REFERENCES `administradores` (`id_administrador`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
