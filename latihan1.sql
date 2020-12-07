-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 10, 2020 at 08:15 AM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `latihan1`
--

-- --------------------------------------------------------

--
-- Table structure for table `detail_bayi`
--

CREATE TABLE `detail_bayi` (
  `id_bayi` varchar(7) NOT NULL,
  `usia_bayi` int(3) NOT NULL,
  `berat_bayi` float NOT NULL,
  `tinggi_bayi` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tb_artikel`
--

CREATE TABLE `tb_artikel` (
  `id_artikel` varchar(5) NOT NULL,
  `judul_artikel` varchar(50) NOT NULL,
  `isi_artikel` text NOT NULL,
  `penulis` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_artikel`
--

INSERT INTO `tb_artikel` (`id_artikel`, `judul_artikel`, `isi_artikel`, `penulis`) VALUES
('AR001', 'Posyandu Bantu Tingkatkan Kesehatan Balita', 'Kesehatan merupakan salah satu nikmat terbesar yang jarang disyukuri, padahal kesehatan adalah pondasi utama untuk menjalani kehidupan.Mengingat kesehatan sangat penting bagi setiap orang, Rumah Zakat hadir memberikan layanan siaga Posyandu di Desa Berdaya Sahkuda Bayu, Sumatera Utara dengan berbagai program. Di antaranya adalah dengan pemberian makanan tambahan, pemberian vaksin rutin, penimbangan balita, pengobatan untuk lansia, dan berbagai pelatihan serta penyuluhan untuk masyarakat. ', 'Kader 1'),
('AR002', 'Artikel kesehatan', 'isi artikel isi artikel isi artikel isi artikel artikel artikel artikel', 'Kader'),
('AR003', 'artikel 2', 'iahhsjskakktruyyyyyyy uuhjjl#nmm; jshsjskks\nbsnanms', 'Kader'),
('AR004', 'ibh dan anak', 'qweerty', 'Kader');

-- --------------------------------------------------------

--
-- Table structure for table `tb_bayi`
--

CREATE TABLE `tb_bayi` (
  `id_bayi` varchar(7) NOT NULL,
  `nama_bayi` varchar(50) NOT NULL,
  `tgl_lahir` date NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `foto_bayi` text NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_bayi`
--

INSERT INTO `tb_bayi` (`id_bayi`, `nama_bayi`, `tgl_lahir`, `jenis_kelamin`, `foto_bayi`, `id`) VALUES
('BY003', 'Bayi Pertama', '2020-07-01', 'Laki-Laki', 'images/Bayi Pertama.jpeg', 4),
('BY004', 'Bayi Kedua', '2020-08-01', 'Perempuan', 'images/Bayi Kedua.jpeg', 4);

-- --------------------------------------------------------

--
-- Table structure for table `tb_detail_bayi`
--

CREATE TABLE `tb_detail_bayi` (
  `id_bayi` varchar(7) NOT NULL,
  `id_usia` varchar(7) CHARACTER SET utf8 NOT NULL,
  `berat_bayi` float NOT NULL,
  `tinggi_bayi` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_detail_bayi`
--

INSERT INTO `tb_detail_bayi` (`id_bayi`, `id_usia`, `berat_bayi`, `tinggi_bayi`) VALUES
('BY003', 'US001', 3.2, 50),
('BY003', 'US002', 5, 56),
('BY003', 'US003', 6, 60),
('BY004', 'US001', 3.8, 50);

-- --------------------------------------------------------

--
-- Table structure for table `tb_jadwal`
--

CREATE TABLE `tb_jadwal` (
  `id_jadwal` varchar(5) NOT NULL,
  `nama_imunisasi` varchar(30) NOT NULL,
  `tgl_imunisasi` date NOT NULL,
  `waktu` time NOT NULL,
  `status` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_jadwal`
--

INSERT INTO `tb_jadwal` (`id_jadwal`, `nama_imunisasi`, `tgl_imunisasi`, `waktu`, `status`) VALUES
('JD001', 'Pertama', '2020-08-17', '10:30:00', 'sudah'),
('JD002', 'Kedua', '2020-08-25', '11:00:00', 'belum'),
('JD003', 'imunisasi ketiga', '2020-08-31', '10:15:00', 'belum'),
('JD004', 'campak', '2020-09-05', '10:00:00', 'belum'),
('JD005', 'covid', '2020-08-26', '23:50:00', 'belum');

-- --------------------------------------------------------

--
-- Table structure for table `tb_kriteria_bb_laki`
--

CREATE TABLE `tb_kriteria_bb_laki` (
  `id_berat` varchar(5) NOT NULL,
  `id_usia` varchar(5) DEFAULT NULL,
  `kurang` float DEFAULT NULL,
  `ideal_bawah` float DEFAULT NULL,
  `ideal` float DEFAULT NULL,
  `ideal_atas` float DEFAULT NULL,
  `lebih` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_kriteria_bb_laki`
--

INSERT INTO `tb_kriteria_bb_laki` (`id_berat`, `id_usia`, `kurang`, `ideal_bawah`, `ideal`, `ideal_atas`, `lebih`) VALUES
('BL001', 'US001', 2.5, 2.9, 3.3, 3.9, 4.3),
('BL002', 'US002', 3.4, 3.9, 4.5, 5.1, 5.7),
('BL003', 'US003', 4.4, 4.9, 5.6, 6.3, 7),
('BL004', 'US004', 5.1, 5.6, 6.4, 7.2, 7.9),
('BL005', 'US005', 5.6, 6.2, 7, 7.9, 8.6),
('BL006', 'US006', 6.1, 6.7, 7.5, 8.4, 9.2),
('BL007', 'US007', 6.4, 7.1, 7.9, 8.9, 9.7),
('BL008', 'US008', 6.7, 7.4, 8.3, 9.3, 10.2),
('BL009', 'US009', 7, 7.7, 8.6, 9.6, 10.5),
('BL010', 'US010', 7.2, 7.9, 8.9, 10, 10.9),
('BL011', 'US011', 7.5, 8.2, 9.2, 10.3, 11.2),
('BL012', 'US012', 7.7, 8.4, 9.4, 10.5, 11.5),
('BL013', 'US013', 7.8, 8.6, 9.6, 10.8, 11.8),
('BL014', 'US014', 8, 8.8, 9.9, 11.1, 12.1),
('BL015', 'US015', 8.2, 9, 10.1, 11.3, 12.4),
('BL016', 'US016', 8.4, 9.2, 10.3, 11.6, 12.7),
('BL017', 'US017', 8.5, 9.4, 10.5, 11.8, 12.9),
('BL018', 'US018', 8.7, 9.6, 10.7, 12, 13.2),
('BL019', 'US019', 8.9, 9.7, 10.9, 12.3, 13.5),
('BL020', 'US020', 9, 9.9, 11.1, 12.5, 13.7),
('BL021', 'US021', 9.2, 10.1, 11.3, 12.7, 14),
('BL022', 'US022', 9.3, 10.3, 11.5, 13, 14.3),
('BL023', 'US023', 9.5, 10.5, 11.8, 13.2, 14.5),
('BL024', 'US024', 9.7, 10.6, 12, 13.4, 14.8),
('BL025', 'US025', 9.8, 10.8, 12.2, 13.7, 15.1),
('BL026', 'US026', 10, 11, 12.4, 13.9, 15.3),
('BL027', 'US027', 10.1, 11.1, 12.5, 14.1, 15.6),
('BL028', 'US028', 10.2, 11.3, 12.7, 14.4, 15.9),
('BL029', 'US029', 10.4, 11.5, 12.9, 14.6, 16.1),
('BL030', 'US030', 10.5, 11.6, 13.1, 14.8, 16.4),
('BL031', 'US031', 10.7, 11.8, 13.3, 15, 16.6),
('BL032', 'US032', 10.8, 11.9, 13.5, 15.2, 16.9),
('BL033', 'US033', 10.9, 12.1, 13.7, 15.5, 17.1),
('BL034', 'US034', 11.1, 12.2, 13.8, 15.7, 17.3),
('BL035', 'US035', 11.2, 12.4, 14, 15.9, 17.6),
('BL036', 'US036', 11.3, 12.5, 14.2, 16.1, 17.8),
('BL037', 'US037', 11.4, 12.7, 14.3, 16.3, 18),
('BL038', 'US038', 11.6, 12.8, 14.5, 16.5, 18.3),
('BL039', 'US039', 11.7, 12.9, 14.7, 16.7, 18.5),
('BL040', 'US040', 11.8, 13.1, 14.8, 16.9, 18.7),
('BL041', 'US041', 11.9, 13.2, 15, 17.1, 19),
('BL042', 'US042', 12.1, 13.4, 15.2, 17.3, 19.2),
('BL043', 'US043', 12.2, 13.5, 15.3, 17.5, 19.4),
('BL044', 'US044', 12.3, 13.6, 15.5, 17.7, 19.7),
('BL045', 'US045', 12.4, 13.8, 15.7, 17.9, 19.9),
('BL046', 'US046', 12.5, 13.9, 15.8, 18.1, 20.1),
('BL047', 'US047', 12.7, 14.1, 16, 18.3, 20.4),
('BL048', 'US048', 12.8, 14.2, 16.2, 18.5, 20.6),
('BL049', 'US049', 12.9, 14.3, 16.3, 18.7, 20.9),
('BL050', 'US050', 13, 14.5, 16.5, 18.9, 21.1),
('BL051', 'US051', 13.1, 14.6, 16.7, 19.1, 21.3),
('BL052', 'US052', 13.3, 14.7, 16.8, 19.3, 21.6),
('BL053', 'US053', 13.4, 14.9, 17, 19.5, 21.8),
('BL054', 'US054', 13.5, 15, 17.2, 19.7, 22.1),
('BL055', 'US055', 13.6, 15.2, 17.3, 19.9, 22.3),
('BL056', 'US056', 13.7, 15.3, 17.5, 20.1, 22.5),
('BL057', 'US057', 13.8, 15.4, 17.7, 20.3, 22.8),
('BL058', 'US058', 13.9, 15.6, 17.8, 20.5, 23),
('BL059', 'US059', 14.1, 15.7, 18, 20.7, 23.3),
('BL060', 'US060', 14.2, 15.8, 18.2, 20.9, 23.5),
('BL061', 'US061', 14.3, 16, 18.3, 21.1, 23.8),
('BR001', 'US001', 2.4, 2.8, 3.2, 3.7, 4.2),
('BR002', 'US002', 3.2, 3.6, 4.2, 4.8, 5.4),
('BR003', 'US003', 4, 4.5, 5.1, 5.9, 6.5),
('BR004', 'US004', 4.6, 5.1, 5.8, 6.7, 7.4),
('BR005', 'US005', 5.1, 5.6, 6.4, 7.3, 8.1),
('BR006', 'US006', 5.5, 6.1, 6.9, 7.8, 8.7),
('BR007', 'US007', 5.8, 6.4, 7.3, 8.3, 9.2),
('BR008', 'US008', 6.1, 6.7, 7.6, 8.7, 9.6),
('BR009', 'US009', 6.3, 7, 7.9, 9, 10),
('BR010', 'US010', 6.6, 7.3, 8.2, 9.3, 10.4),
('BR011', 'US011', 6.8, 7.5, 8.5, 9.6, 10.7),
('BR012', 'US012', 7, 7.7, 8.7, 9.9, 11),
('BR013', 'US013', 7.1, 7.9, 8.9, 10.2, 11.3),
('BR014', 'US014', 7.3, 8.1, 9.2, 10.4, 11.6),
('BR015', 'US015', 7.5, 8.3, 9.4, 10.7, 11.9),
('BR016', 'US016', 7.7, 8.5, 9.6, 10.9, 12.2),
('BR017', 'US017', 7.8, 8.7, 9.8, 11.2, 12.5),
('BR018', 'US018', 8, 8.8, 10, 11.4, 12.7),
('BR019', 'US019', 8.2, 9, 10.2, 11.6, 13),
('BR020', 'US020', 8.3, 9.2, 10.4, 11.9, 13.3),
('BR021', 'US021', 8.5, 9.4, 10.6, 12.1, 13.5),
('BR022', 'US022', 8.7, 9.6, 10.9, 12.4, 13.8),
('BR023', 'US023', 8.8, 9.8, 11.1, 12.6, 14.1),
('BR024', 'US024', 9, 9.9, 11.3, 12.8, 14.3),
('BR025', 'US025', 9.2, 10.1, 11.5, 13.1, 14.6),
('BR026', 'US026', 9.3, 10.3, 11.7, 13.3, 14.9),
('BR027', 'US027', 9.5, 10.5, 11.9, 13.6, 15.2),
('BR028', 'US028', 9.6, 10.7, 12.1, 13.8, 15.4),
('BR029', 'US029', 9.8, 10.8, 12.3, 14, 15.7),
('BR030', 'US030', 10, 11, 12.5, 14.3, 16),
('BR031', 'US031', 10.1, 11.2, 12.7, 14.5, 16.2),
('BR032', 'US032', 10.3, 11.3, 12.9, 14.7, 16.5),
('BR033', 'US033', 10.4, 11.5, 13.1, 15, 16.8),
('BR034', 'US034', 10.5, 11.7, 13.3, 15.2, 17),
('BR035', 'US035', 10.7, 11.8, 13.5, 15.4, 17.3),
('BR036', 'US036', 10.8, 12, 13.7, 15.7, 17.6),
('BR037', 'US037', 11, 12.1, 13.9, 15.9, 17.8),
('BR038', 'US038', 11.1, 12.3, 14, 16.1, 18.1),
('BR039', 'US039', 11.2, 12.5, 14.2, 16.3, 18.4),
('BR040', 'US040', 11.4, 12.6, 14.4, 16.6, 18.6),
('BR041', 'US041', 11.5, 12.8, 14.6, 16.8, 18.9),
('BR042', 'US042', 11.6, 12.9, 14.8, 17, 19.2),
('BR043', 'US043', 11.8, 13.1, 15, 17.3, 19.5),
('BR044', 'US044', 11.9, 13.2, 15.2, 17.5, 19.7),
('BR045', 'US045', 12, 13.4, 15.3, 17.7, 20),
('BR046', 'US046', 12.1, 13.5, 15.5, 17.9, 20.3),
('BR047', 'US047', 12.3, 13.7, 15.7, 18.2, 20.6),
('BR048', 'US048', 12.4, 13.8, 15.9, 18.4, 20.8),
('BR049', 'US049', 12.5, 14, 16.1, 18.6, 21.1),
('BR050', 'US050', 12.6, 14.1, 16.3, 18.9, 21.4),
('BR051', 'US051', 12.8, 14.3, 16.4, 19.1, 21.7),
('BR052', 'US052', 12.9, 14.4, 16.6, 19.3, 22),
('BR053', 'US053', 13, 14.5, 16.8, 19.5, 22.2),
('BR054', 'US054', 13.1, 14.7, 17, 19.8, 22.5),
('BR055', 'US055', 13.2, 14.8, 17.2, 20, 22.8),
('BR056', 'US056', 13.4, 15, 17.3, 20.2, 23.1),
('BR057', 'US057', 13.5, 15.1, 17.5, 20.4, 23.3),
('BR058', 'US058', 13.6, 15.3, 17.7, 20.7, 23.6),
('BR059', 'US059', 13.7, 15.4, 17.9, 20.9, 23.9),
('BR060', 'US060', 13.8, 15.5, 18, 21.1, 24.2),
('BR061', 'US061', 14, 15.7, 18.2, 21.3, 24.4);

-- --------------------------------------------------------

--
-- Table structure for table `tb_kriteria_bb_pr`
--

CREATE TABLE `tb_kriteria_bb_pr` (
  `id_berat` varchar(5) NOT NULL,
  `id_usia` varchar(5) DEFAULT NULL,
  `kurang` float DEFAULT NULL,
  `ideal_bawah` float DEFAULT NULL,
  `ideal` float DEFAULT NULL,
  `ideal_atas` float DEFAULT NULL,
  `lebih` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_kriteria_bb_pr`
--

INSERT INTO `tb_kriteria_bb_pr` (`id_berat`, `id_usia`, `kurang`, `ideal_bawah`, `ideal`, `ideal_atas`, `lebih`) VALUES
('BR001', 'US001', 2.4, 2.8, 3.2, 3.7, 4.2),
('BR002', 'US002', 3.2, 3.6, 4.2, 4.8, 5.4),
('BR003', 'US003', 4, 4.5, 5.1, 5.9, 6.5),
('BR004', 'US004', 4.6, 5.1, 5.8, 6.7, 7.4),
('BR005', 'US005', 5.1, 5.6, 6.4, 7.3, 8.1),
('BR006', 'US006', 5.5, 6.1, 6.9, 7.8, 8.7),
('BR007', 'US007', 5.8, 6.4, 7.3, 8.3, 9.2),
('BR008', 'US008', 6.1, 6.7, 7.6, 8.7, 9.6),
('BR009', 'US009', 6.3, 7, 7.9, 9, 10),
('BR010', 'US010', 6.6, 7.3, 8.2, 9.3, 10.4),
('BR011', 'US011', 6.8, 7.5, 8.5, 9.6, 10.7),
('BR012', 'US012', 7, 7.7, 8.7, 9.9, 11),
('BR013', 'US013', 7.1, 7.9, 8.9, 10.2, 11.3),
('BR014', 'US014', 7.3, 8.1, 9.2, 10.4, 11.6),
('BR015', 'US015', 7.5, 8.3, 9.4, 10.7, 11.9),
('BR016', 'US016', 7.7, 8.5, 9.6, 10.9, 12.2),
('BR017', 'US017', 7.8, 8.7, 9.8, 11.2, 12.5),
('BR018', 'US018', 8, 8.8, 10, 11.4, 12.7),
('BR019', 'US019', 8.2, 9, 10.2, 11.6, 13),
('BR020', 'US020', 8.3, 9.2, 10.4, 11.9, 13.3),
('BR021', 'US021', 8.5, 9.4, 10.6, 12.1, 13.5),
('BR022', 'US022', 8.7, 9.6, 10.9, 12.4, 13.8),
('BR023', 'US023', 8.8, 9.8, 11.1, 12.6, 14.1),
('BR024', 'US024', 9, 9.9, 11.3, 12.8, 14.3),
('BR025', 'US025', 9.2, 10.1, 11.5, 13.1, 14.6),
('BR026', 'US026', 9.3, 10.3, 11.7, 13.3, 14.9),
('BR027', 'US027', 9.5, 10.5, 11.9, 13.6, 15.2),
('BR028', 'US028', 9.6, 10.7, 12.1, 13.8, 15.4),
('BR029', 'US029', 9.8, 10.8, 12.3, 14, 15.7),
('BR030', 'US030', 10, 11, 12.5, 14.3, 16),
('BR031', 'US031', 10.1, 11.2, 12.7, 14.5, 16.2),
('BR032', 'US032', 10.3, 11.3, 12.9, 14.7, 16.5),
('BR033', 'US033', 10.4, 11.5, 13.1, 15, 16.8),
('BR034', 'US034', 10.5, 11.7, 13.3, 15.2, 17),
('BR035', 'US035', 10.7, 11.8, 13.5, 15.4, 17.3),
('BR036', 'US036', 10.8, 12, 13.7, 15.7, 17.6),
('BR037', 'US037', 11, 12.1, 13.9, 15.9, 17.8),
('BR038', 'US038', 11.1, 12.3, 14, 16.1, 18.1),
('BR039', 'US039', 11.2, 12.5, 14.2, 16.3, 18.4),
('BR040', 'US040', 11.4, 12.6, 14.4, 16.6, 18.6),
('BR041', 'US041', 11.5, 12.8, 14.6, 16.8, 18.9),
('BR042', 'US042', 11.6, 12.9, 14.8, 17, 19.2),
('BR043', 'US043', 11.8, 13.1, 15, 17.3, 19.5),
('BR044', 'US044', 11.9, 13.2, 15.2, 17.5, 19.7),
('BR045', 'US045', 12, 13.4, 15.3, 17.7, 20),
('BR046', 'US046', 12.1, 13.5, 15.5, 17.9, 20.3),
('BR047', 'US047', 12.3, 13.7, 15.7, 18.2, 20.6),
('BR048', 'US048', 12.4, 13.8, 15.9, 18.4, 20.8),
('BR049', 'US049', 12.5, 14, 16.1, 18.6, 21.1),
('BR050', 'US050', 12.6, 14.1, 16.3, 18.9, 21.4),
('BR051', 'US051', 12.8, 14.3, 16.4, 19.1, 21.7),
('BR052', 'US052', 12.9, 14.4, 16.6, 19.3, 22),
('BR053', 'US053', 13, 14.5, 16.8, 19.5, 22.2),
('BR054', 'US054', 13.1, 14.7, 17, 19.8, 22.5),
('BR055', 'US055', 13.2, 14.8, 17.2, 20, 22.8),
('BR056', 'US056', 13.4, 15, 17.3, 20.2, 23.1),
('BR057', 'US057', 13.5, 15.1, 17.5, 20.4, 23.3),
('BR058', 'US058', 13.6, 15.3, 17.7, 20.7, 23.6),
('BR059', 'US059', 13.7, 15.4, 17.9, 20.9, 23.9),
('BR060', 'US060', 13.8, 15.5, 18, 21.1, 24.2),
('BR061', 'US061', 14, 15.7, 18.2, 21.3, 24.4);

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL,
  `unique_id` varchar(23) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `no_telp` varchar(13) NOT NULL,
  `level` enum('1','2','3') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id`, `unique_id`, `nama`, `email`, `encrypted_password`, `salt`, `no_telp`, `level`) VALUES
(1, '5f0ffb4f429309.88523551', 'Admin', 'admin', 'wbCfjRdGJMjrvl+xmWjYCkR6ZngyMTkxNzEzM2Zm', '21917133ff', '082245655379', '1'),
(3, '5f13e1a8c050a9.13017690', 'Bidan', 'bidan', 'XwcbeefPyHOEDd/c/PB6xvvZYwQ3MzYxMzBkNDY2', '736130d466', '0812345678901', '2'),
(4, '5f1d08187511a0.76156974', 'User', 'user', 'pn62fXajmffewYadZ5aJSJlIh+AxNWE3ZjY1YWRl', '15a7f65ade', '082234567890', '3'),
(5, '5f3ccd0a381600.82068704', 'ibu bayi 1', 'ibu', 'ucMuSAoy7dCHcaKwM1tbkYfXL280NTRlNmQwNmMx', '454e6d06c1', '085331278987', '3'),
(6, '5f3ce4e913d061.79559108', 'ibu bayi dua', 'user2', 'sdsn32e1b5RCbKmPLbLBUAghl0JjZDUyOWY5YzNi', 'cd529f9c3b', '0864464644664', '3');

-- --------------------------------------------------------

--
-- Table structure for table `tb_usia`
--

CREATE TABLE `tb_usia` (
  `id_usia` varchar(7) NOT NULL,
  `usia` varchar(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tb_usia`
--

INSERT INTO `tb_usia` (`id_usia`, `usia`) VALUES
('US001', '0'),
('US002', '1'),
('US003', '2'),
('US004', '3'),
('US005', '4'),
('US006', '5'),
('US007', '6'),
('US008', '7'),
('US009', '8'),
('US010', '9'),
('US011', '10'),
('US012', '11'),
('US013', '12'),
('US014', '13'),
('US015', '14'),
('US016', '15'),
('US017', '16'),
('US018', '17'),
('US019', '18'),
('US020', '19'),
('US021', '20'),
('US022', '21'),
('US023', '22'),
('US024', '23'),
('US025', '24'),
('US026', '25'),
('US027', '26'),
('US028', '27'),
('US029', '28'),
('US030', '29'),
('US031', '30'),
('US032', '31'),
('US033', '32'),
('US034', '33'),
('US035', '34'),
('US036', '35'),
('US037', '36'),
('US038', '37'),
('US039', '38'),
('US040', '39'),
('US041', '40'),
('US042', '41'),
('US043', '42'),
('US044', '43'),
('US045', '44'),
('US046', '45'),
('US047', '46'),
('US048', '47'),
('US049', '48'),
('US050', '49'),
('US051', '50'),
('US052', '51'),
('US053', '52'),
('US054', '53'),
('US055', '54'),
('US056', '55'),
('US057', '56'),
('US058', '57'),
('US059', '58'),
('US060', '59'),
('US061', '60');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_bayi`
--
ALTER TABLE `detail_bayi`
  ADD KEY `id_bayi` (`id_bayi`);

--
-- Indexes for table `tb_artikel`
--
ALTER TABLE `tb_artikel`
  ADD PRIMARY KEY (`id_artikel`);

--
-- Indexes for table `tb_bayi`
--
ALTER TABLE `tb_bayi`
  ADD PRIMARY KEY (`id_bayi`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `tb_detail_bayi`
--
ALTER TABLE `tb_detail_bayi`
  ADD KEY `id_usia` (`id_usia`),
  ADD KEY `id_bayi` (`id_bayi`);

--
-- Indexes for table `tb_jadwal`
--
ALTER TABLE `tb_jadwal`
  ADD PRIMARY KEY (`id_jadwal`);

--
-- Indexes for table `tb_kriteria_bb_laki`
--
ALTER TABLE `tb_kriteria_bb_laki`
  ADD PRIMARY KEY (`id_berat`),
  ADD KEY `id_usia` (`id_usia`);

--
-- Indexes for table `tb_kriteria_bb_pr`
--
ALTER TABLE `tb_kriteria_bb_pr`
  ADD PRIMARY KEY (`id_berat`),
  ADD KEY `id_usia` (`id_usia`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_usia`
--
ALTER TABLE `tb_usia`
  ADD PRIMARY KEY (`id_usia`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_user`
--
ALTER TABLE `tb_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_bayi`
--
ALTER TABLE `tb_bayi`
  ADD CONSTRAINT `tb_bayi_ibfk_1` FOREIGN KEY (`id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_detail_bayi`
--
ALTER TABLE `tb_detail_bayi`
  ADD CONSTRAINT `tb_detail_bayi_ibfk_1` FOREIGN KEY (`id_bayi`) REFERENCES `tb_bayi` (`id_bayi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tb_detail_bayi_ibfk_2` FOREIGN KEY (`id_usia`) REFERENCES `tb_usia` (`id_usia`);

--
-- Constraints for table `tb_kriteria_bb_laki`
--
ALTER TABLE `tb_kriteria_bb_laki`
  ADD CONSTRAINT `tb_kriteria_bb_laki_ibfk_1` FOREIGN KEY (`id_usia`) REFERENCES `tb_usia` (`id_usia`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tb_kriteria_bb_pr`
--
ALTER TABLE `tb_kriteria_bb_pr`
  ADD CONSTRAINT `tb_kriteria_bb_pr_ibfk_1` FOREIGN KEY (`id_usia`) REFERENCES `tb_usia` (`id_usia`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
