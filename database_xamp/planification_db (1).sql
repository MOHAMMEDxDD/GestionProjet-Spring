-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Jan 06, 2026 at 05:04 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `planification_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `id` bigint(20) NOT NULL,
  `budget` double DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL,
  `budget` double DEFAULT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `statut` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `budget`, `date_debut`, `date_fin`, `description`, `nom`, `statut`) VALUES
(1, 3500, NULL, NULL, 'Application web de gestion de stock', 'Gestion Stock', 'EN_COURS'),
(2, 5000, NULL, NULL, 'application gestion des entree sortiee', 'projet ocp', 'TERMINE'),
(3, 5000, '2026-01-06', '2026-01-22', 'gestion de presence ', 'projet estsb', 'EN_COURS');

-- --------------------------------------------------------

--
-- Table structure for table `task`
--

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL,
  `date_echeance` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `progression` int(11) NOT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `urgent` bit(1) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `responsable_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

CREATE TABLE `tasks` (
  `id` bigint(20) NOT NULL,
  `date_echeance` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `progression` int(11) NOT NULL,
  `statut` varchar(255) DEFAULT NULL,
  `titre` varchar(255) DEFAULT NULL,
  `urgent` bit(1) NOT NULL,
  `project_id` bigint(20) DEFAULT NULL,
  `responsable_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tasks`
--

INSERT INTO `tasks` (`id`, `date_echeance`, `description`, `progression`, `statut`, `titre`, `urgent`, `project_id`, `responsable_id`) VALUES
(2, '2026-01-07', NULL, 50, 'EN_COURS', 'devlope GUI', b'0', 2, 4),
(3, '2026-01-14', NULL, 100, 'TERMINE', 'full stack ', b'0', 3, 5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `nom_complet` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `salaire` double DEFAULT NULL,
  `poste` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `nom_complet`, `password`, `role`, `username`, `salaire`, `poste`, `telephone`) VALUES
(1, 'Monsieur Le Chef', '123', 'RESPONSABLE', 'admin', NULL, NULL, NULL),
(3, 'Hassan Dev', '123', 'DEV', 'hassan', NULL, NULL, NULL),
(4, 'nabil ', 'nabil123', 'DEV', 'nabil@gmail.com', NULL, NULL, NULL),
(5, 'mohammed bziz ', 'mohammed123', 'DEV', 'mohammed@gmail.com', 15000, 'Développeur Fullstack', '0619779724');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK48iyd7nsxnbck3bfipo8tw9xr` (`responsable_id`),
  ADD KEY `FK59mygkh6yxl8yj6j8ocg1k73a` (`project_id`);

--
-- Indexes for table `tasks`
--
ALTER TABLE `tasks`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsfhn82y57i3k9uxww1s007acc` (`project_id`),
  ADD KEY `FK1xhw3ov0lob6j2jir2jj8nuly` (`responsable_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `task`
--
ALTER TABLE `task`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tasks`
--
ALTER TABLE `tasks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `FK48iyd7nsxnbck3bfipo8tw9xr` FOREIGN KEY (`responsable_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FK59mygkh6yxl8yj6j8ocg1k73a` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`),
  ADD CONSTRAINT `FKk8qrwowg31kx7hp93sru1pdqa` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`);

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `FK1xhw3ov0lob6j2jir2jj8nuly` FOREIGN KEY (`responsable_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKsfhn82y57i3k9uxww1s007acc` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
