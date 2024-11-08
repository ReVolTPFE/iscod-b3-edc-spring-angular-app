-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : db:3306
-- Généré le : ven. 08 nov. 2024 à 12:50
-- Version du serveur : 11.4.2-MariaDB-ubu2404
-- Version de PHP : 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `edc`
--

-- --------------------------------------------------------

--
-- Structure de la table `project`
--

CREATE TABLE `project` (
  `id` int(11) NOT NULL,
  `started_at` date DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `project`
--

INSERT INTO `project` (`id`, `started_at`, `name`, `description`) VALUES
(7, '2024-11-08', 'Agile Sprint Dashboard', 'An internal tool to help development teams plan, track, and visualize agile sprints and individual tasks.'),
(8, '2024-11-08', 'Code Quality Monitoring Tool', 'A tool to automatically track code quality metrics, such as test coverage, linting issues, and complexity analysis.');

-- --------------------------------------------------------

--
-- Structure de la table `task`
--

CREATE TABLE `task` (
  `id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `task`
--

INSERT INTO `task` (`id`, `project_id`) VALUES
(26, 7),
(27, 7),
(28, 7),
(29, 8),
(30, 8),
(31, 8);

-- --------------------------------------------------------

--
-- Structure de la table `task_history`
--

CREATE TABLE `task_history` (
  `due_date` date DEFAULT NULL,
  `ended_at` date DEFAULT NULL,
  `id` int(11) NOT NULL,
  `task_id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `description` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `task_history`
--

INSERT INTO `task_history` (`due_date`, `ended_at`, `id`, `task_id`, `name`, `priority`, `status`, `description`) VALUES
('2024-11-30', NULL, 61, 26, 'Develop User Stories Module', 'LOW', 'TODO', 'Design and implement a module where users can create, view, and prioritize user stories for each sprint.'),
('2024-11-20', NULL, 62, 27, 'Implement Sprint Analytics', 'MEDIUM', 'ONGOING', 'Build a dashboard to display metrics like sprint velocity, burndown charts, and completed tasks.'),
('2024-11-10', NULL, 63, 28, 'Integrate Task Comments and Mentions', 'HIGH', 'DONE', 'Add functionality for team members to comment on tasks, tag colleagues, and receive notifications.'),
('2024-11-18', NULL, 64, 27, 'Implement Sprint Analytics', 'MEDIUM', 'ONGOING', 'Build a dashboard to display metrics like sprint velocity, burndown charts, and completed tasks.'),
('2024-11-10', NULL, 65, 28, 'Integrate Task Comments and Mentions', 'HIGH', 'DONE', 'Add functionality for team members to comment on tasks, tag colleagues, and receive notifications.'),
('2024-11-08', '2024-11-08', 66, 28, 'Integrate Task Comments and Mentions', 'HIGH', 'DONE', 'Add functionality for team members to comment on tasks, tag colleagues, and receive notifications.'),
('2024-11-23', NULL, 67, 29, 'Set Up Code Quality Report Generation', 'MEDIUM', 'TODO', 'Develop a feature to generate and export code quality reports for each project repository.'),
('2024-11-29', NULL, 68, 30, 'Automate Test Coverage Analysis', 'MEDIUM', 'ONGOING', 'Integrate with CI/CD pipeline to measure and display test coverage for new commits.'),
('2024-11-30', NULL, 69, 31, 'Implement Code Complexity Alerts', 'LOW', 'TODO', 'Create a system to monitor and alert team members if code complexity exceeds certain thresholds.');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `username`) VALUES
(19, 'john@codesolutions.com', 'azerty', 'John Doe'),
(20, 'nicolas@codesolutions.com', 'azerty', 'Nicolas'),
(21, 'mariana@codesolutions.com', 'azerty', 'Mariana');

-- --------------------------------------------------------

--
-- Structure de la table `user_project_role`
--

CREATE TABLE `user_project_role` (
  `id` int(11) NOT NULL,
  `project_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user_project_role`
--

INSERT INTO `user_project_role` (`id`, `project_id`, `user_id`, `role`) VALUES
(21, 7, 21, 'ADMIN'),
(22, 8, 21, 'ADMIN'),
(23, 7, 19, 'MEMBER'),
(24, 8, 20, 'GUEST');

-- --------------------------------------------------------

--
-- Structure de la table `user_task`
--

CREATE TABLE `user_task` (
  `task_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `user_task`
--

INSERT INTO `user_task` (`task_id`, `user_id`) VALUES
(26, 21),
(27, 21),
(28, 21),
(29, 21),
(30, 21),
(31, 21);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKk8qrwowg31kx7hp93sru1pdqa` (`project_id`);

--
-- Index pour la table `task_history`
--
ALTER TABLE `task_history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKer57q2libi1e9njpj6faoxd2i` (`task_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`);

--
-- Index pour la table `user_project_role`
--
ALTER TABLE `user_project_role`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK9l1icyrvwmfc2lr9fskttk85b` (`project_id`),
  ADD KEY `FKyaccfypicvgcice7sqocix13` (`user_id`);

--
-- Index pour la table `user_task`
--
ALTER TABLE `user_task`
  ADD PRIMARY KEY (`task_id`,`user_id`),
  ADD KEY `FKr2jik008e3jx6r1fal5e9aq1n` (`user_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `project`
--
ALTER TABLE `project`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `task`
--
ALTER TABLE `task`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT pour la table `task_history`
--
ALTER TABLE `task_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=70;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `user_project_role`
--
ALTER TABLE `user_project_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `task`
--
ALTER TABLE `task`
  ADD CONSTRAINT `FKk8qrwowg31kx7hp93sru1pdqa` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`);

--
-- Contraintes pour la table `task_history`
--
ALTER TABLE `task_history`
  ADD CONSTRAINT `FKer57q2libi1e9njpj6faoxd2i` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);

--
-- Contraintes pour la table `user_project_role`
--
ALTER TABLE `user_project_role`
  ADD CONSTRAINT `FK9l1icyrvwmfc2lr9fskttk85b` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
  ADD CONSTRAINT `FKyaccfypicvgcice7sqocix13` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `user_task`
--
ALTER TABLE `user_task`
  ADD CONSTRAINT `FKr2jik008e3jx6r1fal5e9aq1n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKvs34bjkmpbk2e54qlrol3ilt` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
