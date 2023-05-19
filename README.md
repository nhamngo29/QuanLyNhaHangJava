# QUẢN LÝ NHÀ HÀNG
<p>Language:Java</p>
<p>Database:SQL Saver</p>
<hr/>
<p>Member<p> 
<ul>
  <li>Nguyễn Nhâm Ngọ (https://github.com/nhamngo29)</li>
  <li>Trần Quang Khải</li>
  <li>Nguyễn Quốc Cường</li>
</ul>

<div align="center">

# PHÂN CÔNG CÔNG VIỆC

</div>

| STT | Họ tên  | Nôi dung công việc | Ghi chú |
|--------------|-------|------|-------|
| 1 | Nguyễn Nhâm Ngọ | - Thiết kệ CSDL,nhập liêu,proc,trigger nhập liệu bảng </br> - Form quản lý thực đơn , from nhập xuất kho , from hóa đơn , from nhân viên , from bàn ăn ,from tài khoản frm main , from đặt bàn, from order xử lý nghiệp vụ tất cả các form | Hoàn thành 100%  |
| 2 | Nguyễn Quốc Cường | - From quản lý hóa đơn,thông kê,vẽ sơ đồ ,viết trigger nghiệp của form | Hoàn thành 100% |
| 3  | Trần Quang Khải | - Thiết kế CSDL,giao diện form order | Hoàn thành 80% |

### Mục lục
[I. PHÂN TÍCH](#PhanTich)

- [1.1 HIỆN TRẠNG-KHẢO SÁT](#HienTrangKhaoSat)
- [1.2 YÊU CẦU HỆ THỐNG CẦN LÀM](#YeuCauHeThongCanLam)
- [1.3	USE CAS](#useCase)

[II. THIẾT KẾ](#ThietKe)

- [2.1	MÔ HÌNH TRIỂN KHAI](#MoHinhTrienKhai)
- [2.2	THIẾT KẾ CSDL](#ThietKeCSDL)
  - [2.2.1	Sơ đồ quan hệ thực thể](#SoDoQuanHeThucThe)
  - [2.2.2	Thiết kế chi tiết các thực thể](#ThietKeChiTietCacThucThe)
- [2.3	THIẾT KẾ GIAO DIỆN (MOCKUP)](#ThietKeGiaoDien)
  - [2.3.1	Sơ đồ tổ chức giao diện](#SoDoToChucGiaoDien)
  - [2.3.2	Giao diện mockup](#GiaoDien)
 
[III	KIỂM THỬ](#KiemThu)

[IV	ĐÓNG GÓI VÀ TRIỂN KHAI](#DongGoiVaTrienKhai)

- [4.1	HƯỚNG DẪN CHUYỂN ĐỔI JAR THÀNH EXE](#HuongDanChuyenDoiJarThanhExe)
- [4.2	HƯỚNG DẪN CÀI ĐẶT TRIỂN KHAI](#HuongDanCaiDatTrienKhai)
- [4.3	HƯỚNG DẪN SỬ DỤNG PHẦN MỀM](#HungDanSuDungPhanMem)

[V ĐÓNG GÓI VÀ TRIỂN KHAI](#DongGoiVaTrienKhai)

- [5.1	SẢN PHẨM PHẦN MỀM](#SanPhamPhanMem)
- [5.2	HƯỚNG DẪN CÀI ĐẶT](#SanPhamPhanMem)

[VI. Tổng kết](#TongKet)


<a name="PhanTich"></a>
## I. PHÂN TÍCH

<a name="HienTrangKhaoSat"></a>
### 1.1	HIỆN TRẠNG-KHẢO SÁT

Phần mềm quản lý nhà hàng, quán ăn, quán cafe...`Restaurant Victory` cung cấp giải pháp quản lý nhà hàng toàn diện, triển khai dễ dàng, nhanh chóng với nhiều tính năng ưu việt:
-	Đáp ứng tốt theo mọi hình thức: Gọi món phục vụ tại bàn, tự chọn (buffet, gọi món), phục vụ nhanh.
- Đáp ứng nghiệp vụ cho: Thu ngân, nhân viên phục vụ và quản lý nhà hàng.
- Hỗ trợ các nghiệp vụ quản lý tồn kho, chi phí và lương nhân viên.
- Chạy tốt trên thiết bị: Máy tính
<a name="YeuCauHeThongCanLam"></a>
### 1.2	YÊU CẦU HỆ THỐNG CẦN LÀM

`Restaurant Victory` mong muốn xây dựng một phần mềm để giải quyết khó khăn trên.
**Yêu cầu chức năng nghiệp vụ:**
- Quản lý
  -  Danh sách món ăn
  - Danh Sách Nhân viên
  - Gọi món ăn
  - Xem món ăn
  - Xem Yêu cầu khác
  - Xem đặt bàn
- Thống kê
  - Doanh thu theo ngày / tháng
  - Lương nhân viên
  - Hàng hóa
  - Nguyên liệu
  - Danh sách Món ăn
#### Yêu cầu về bảo mật
  - Phần mềm được viết cho 2 đối tượng sử dụng là **Nhân viên, Quản lý** với yêu cầu bảo mật như sau: 
  - **Nhân viên** và **Quản lý** phải đăng nhập mới được sử dụng phần mềm
  - **Nhân viên** sẽ được phân thành **Phục vụ** và **Thu ngân**
  - Chức năng **Khóa** sổ sẽ yêu cầu nhập Pass của **Thu ngân** để hoạt động
  - Ngoại trừ chức năng **Khóa sổ** thì **Quản lý** sẽ sử dụng được tất cả các chức năng kèm thêm các chức năng quản lý: **DS Nhân viên, Chấm công, Doanh thu tháng/ngày, Xếp lịch, Thêm món mới, Xem / Chỉnh sửa hàng hóa**
  - Tại phần Thanh toán, **Nhân viên** có thể nhập mã Thành viên để hóa đơn được giảm giá, mức giảm sẽ tính theo số liệu mà nhà hàng đưa ra.
#### Yêu cầu về môi trường công nghệ:
  Ứng dụng phải được thực với công nghệ Swing và JDBC chạy trên mọi hệ điều hành với môi trường JDK tối thiểu 1.8
  Hệ quản trị CSDL SQL Server 2008 trở lên
<a name="useCase"></a>
### 1.3	USE CASE

  Use case là sơ đồ tổng quan về mặt chức năng và phân vai trò người sử dụng. Dựa vào yêu cầu hệ thống của khách hàng, chúng ta có thể phác thảo sơ đồ use case như sau.
  ![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/fe1fe495-1067-4197-b1f2-253c1696a323)
- CHI TIẾT CÁC CHỨC NĂNG
    - Mỗi chức năng quản lý bao gồm các chức năng con
      - Xem: xem tất cả và chi tiết một mục
      - Thêm: thêm mới vào cơ sở dữ liệu
      - Xóa: xóa theo mã
      - Sửa: cập nhật dữ liệu đang xem
      - Tìm kiếm: tìm kiếm theo điều kiện
      - Điều hướng: di chuyển đến dữ liệu của bản ghi chi tiết trước và sau
      - Các chức năng thao tác dữ liệu (thêm, sửa, xóa) cần được kiểm lỗi hợp lý với dữ liệu
    - Chức năng thống kê doanh thu theo ngày/tháng:
      - Doanh thu theo ngày
        - Số Bàn
        - Mã nhân viên
    - Chi tiết hóa đơn
      - Mã hóa đơn chi tiết
      - Mã hóa đơn
      - Mã món ăn 
      - Số lượng
    - Danh sách bàn
      - Mã bàn 
      - Loại bàn
      - Ghi chú
    - Danh mục
      - Mã danh mục 
      - Tên danh mục
      - Mô tả
    - Đặt bàn
      - Mã đặt bàn 
      - Mã bàn
      - Mã khách hàng
      - Ngày đặt bàn
      - Số lượng khách
      - Ghi chú 
    - Món ăn
      - Mã món ăn
      - Tên món ăn 
      - Giá tiền
      - Mã danh mục
    - Nhân Viên
      - Mã nhân viên
      - Password
      - Họ tên
      - Số điện thoại
      - Chức vụ 
      - Giới tính
<a name="ThietKe"></a>
## II. THIẾT KẾ

<a name="MoHinhTrienKhai"></a>
### 2.1	MÔ HÌNH TRIỂN KHAI

Ứng dụng phần mềm được xây dựng để phục vụ cho nhiều người dùng nhưng cơ sở dữ liệu thì lưu trữ tập trung.
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/dd19ca7a-5b5f-478b-b4db-d09636e792c7)
- Cần một máy cài SQL Server 2008+. Máy này cần hệ điều hành window xp trở lên
-	Các máy nhân viên phòng đào tạo cài phần mềm edusys. Các máy nhân viên cần JDK 1.8+ với hệ điều hành bất ký
<a name="ThietKeCSDL"></a>
### 2.2	THIẾT KẾ CSDL

<a name="SoDoQuanHeThucThe"></a>
#### 2.2.1	Sơ đồ quan hệ thực thể

##### *2.2.1.1	ERD Diagram level 1*
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/bcbd3a29-0746-4f9f-8fe3-17d1b179b728)
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/505490d1-131f-4c0c-9b51-77661717d254)
<a name="ThietKeChiTietCacThucThe"></a>
#### 2.2.2	Thiết kế chi tiết các thực thể

- Bảng Chi tiết hóa đơn
 
| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
| MaHoaDonCT | INT | PK, NOT NULL | Mã hóa đơn chi tiết |
| MaHoaDon | INT | FK,NOT NULL | Mã hóa đơn |
| MaMonAn  | NVARCHAR(15) |FK,NOT NULL | Mã món ăn  |
| SoLuong  | INT | NOT NULL | Số lượng |

- Bảng Bàn Ăn

| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
|MaBan | INT | PK, NOT NULL | Mã Bàn |
| LoaiBan | INT | NOT NULL | Loại Bàn |
| TrangThai  | NVARCHAR(50) | NOT NULL | Trạng Thái   |

- Bảng Đặt Bàn

| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
| MaDatBan | NVARCHAR(10) | PK, NOT NULL | Mã đặt bàn  |
|TenKH | NVARCHAR(50) | NOT NULL | Tên khách hàng |
| SDTKH  | NVARCHAR(15) | NOT NULL | Số điện thoại   |
| NgayDatBan | DATE | NOT NULL | Ngày đặt bàn |
| GioDat  | NVARCHAR(10) | NOT NULL | Giờ đặt   |
| SoNguoi | Số người | NOT NULL | Số người |
| GhiChu  | NVARCHAR(50) | NULL | Ghi chú |
| MaNV  | NVARCHAR(5) | FK, NOT NULL | Mã nhân viên |

- Bảng Hóa Đơn

| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
| MaHoaDon | INT | PK, NOT NULL | Mã hóa đơn  |
| MaBan | NVARCHAR(10) | FK, NOT NULL | Mã bàn |
| TongTien  | MONNEY | NOT NULL | Tổng tiền |
| MaNV | NVARCHAR(5) | FK,NOT NULL | Mã nhân viên |
| NgayTao  | Date | NOT NULL | Ngày tạo   |
| TrangThai | NVARCHAR(50) | NOT NULL | Số người |
| GhiChu  | NVARCHAR(50) | NOT NULL | Trạng thái |

- Bảng Thực Đơn

| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
| MaMon | NVARCHAR(15) | PK, NOT NULL | Mã món ăn  |
| TenMon | NVARCHAR(50) | NOT NULL | Tên món ăn |
| GiaTien  | MONNEY | NOT NULL | Giá tiền |
| HinhAnh | NVARCHAR(50) | NOT NULL | Hình ảnh |
| Loai  | NVARCHAR(50) | NOT NULL | Loại |

- Bảng Nhân Viên

| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
| MaNV | NVARCHAR(5) | PK, NOT NULL | Mã nhân viên  |
| Password | NVARCHAR(50) | NOT NULL | Mật khẩu |
| HoTen  | NVARCHAR(50) | NOT NULL | Họ và tên |
| SoDT | NVARCHAR(15) | NOT NULL | Số điện thoại |
| ChucVu  | NVARCHAR(30) | NOT NULL | Chức vụ |
| GioiTinh  | BIT | NOT NULL | Giới tính |

- Bảng Kho hàng

| Tên cột | Kiểu  |Ràng buộc | Ghi chú |
|--------------|-------|------|-------|
| MaHangHoa | NVARCHAR(50) | PK, NOT NULL | Mã hàng hóa   |
| TenHangHoa | NVARCHAR(50) | NOT NULL | Tên hàng hóa |
| NgayNhap  | DATE | NOT NULL | Ngày nhập hàng |
| DonVi | NVARCHAR(50) | NOT NULL | Đơn vị |
| SoLuong  | int | NOT NULL | Số lượng |
| ChiPhi  | FLOAT | NOT NULL | Chi Phí |
| TongChiPhi  | FLOAT | NOT NULL | Tổng chi phí |
<a name="ThietKeGiaoDien"></a>
### 2.3	THIẾT KẾ GIAO DIỆN (MOCKUP)

<a name="SoDoToChucGiaoDien"></a>
#### 2.3.1	Sơ đồ tổ chức giao diện

Giao diện chính là một cửa sổ chứa menu chính và toolbar. Thông qua đó để đi đến các giao diện thành viên để thực hiện các chức năng trong hệ thống.
Theo yêu cầu thì mọi nhân viên phải đăng nhập trước khi sử dụng ứng dụng nên form đăng nhập xuất hiện trước để yêu cầu đăng nhập.
Ngoài ra mỗi ứng dụng trong thời gian khởi động cần có một màn hình chào cùng với thanh tiến trình để người có cảm giác ứng dụng đang khởi động.
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/96d120fd-07c6-401f-8e2f-fd2cd84d4927)
<a name="GiaoDien"></a>
### 2.3.2	Giao diện mockup:

- Login

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/8c4015ab-cb44-4103-987a-8b31d58b078c)

- Order

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/e8575dc4-06f2-40e5-9014-4275f3168f66)

- Quản lý doanh thu

  - Danh sách,biểu đồ

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/453352de-08f0-4cf6-a9ba-d041ee80bdf6)

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/4c6e252e-b318-4e7a-91b3-2f44861cbebf)

- Quản lý bàn

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/73f56e25-91ad-4e1d-ad8a-5a8848e438de)

- Quản lý nhân viên

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/4acc34b3-4bbf-47ba-8520-bd08a798205c)

- Quản lý nhập xuất kho :
 - Danh sách,Cập nhật
 
  ![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/bbb161b2-85cf-466b-8d8a-79e625cc7cb2)
  
  ![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/0c201818-457c-46c3-9f05-84c91aee2af9)
  
  - Phiếu xuất hàng
 
  ![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/34a659b1-06dc-49c8-aec1-f7140f68c723)
  
- Quản lý thực đơn

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/df99dad2-954e-48e9-80eb-07c0c84a049e)

- Thông tin tài khoản

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/76952900-27e1-4778-ba9f-c6ffeeecaf23)

- Đổi mật khẩu

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/c1c692df-e95b-4e4b-b57b-2932daf8c583)

<a name="KiemThu"></a>
## III	KIỂM THỬ


-	KIỂM THỬ FORM QUẢN LÝ NHÂN VIÊN

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/6f2eb7b9-c123-4b46-a834-c5d4fe71df91)

-	KIỂM THỬ FORM QUẢN LÝ ĐẶT BÀN

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/91c57f71-4742-41f3-95a7-c60d161140a6)

-	KIỂM THỬ FORM QUẢN LÝ THỰC ĐƠN

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/ca6a7948-d516-4439-b4ca-4769860030cc)

-	KIỂM THỬ FORM QUẢN LÝ DOANH THU THEO NGÀY

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/153a91d3-cf2a-4e2a-b240-a80611824b82)
 

-	KIỂM THỬ FORM LOGIN

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/e66ac5ae-1841-4d0f-b084-909511ed3cc5)

-	KIỂM THỦ FORM ĐỔI MẬT KHẨU

![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/9f5cf83e-da80-4091-b1d8-cbefb14a6e75)

<a name="DongGoiVaTrienKhai"></a>
## IV	ĐÓNG GÓI VÀ TRIỂN KHAI

<a name="HuongDanChuyenDoiJarThanhExe"></a>
### 4.1	HƯỚNG DẪN CHUYỂN ĐỔI JAR THÀNH EXE

- Sử dụng phaần mềm EXE4J để chuyển đổi jar sang exe
- Sử dụng phần mềm InnoSetup để đóng gói'
<a name="HuongDanCaiDatTrienKhai"></a>
### 4.2	HƯỚNG DẪN CÀI ĐẶT TRIỂN KHAI

- Cài SQL Server 2008 trở lên
- Tạo CSDL QuanLyNhaHang bằng cách chạy file SQL_QLNhaHang.sql
- Cài đặt JDK 1.8 trở lên
- Chạy file setup
<a name="HungDanSuDungPhanMem"></a>
### 4.3	HƯỚNG DẪN SỬ DỤNG PHẦN MỀM

- Đăng nhập với tài khoản username: NV01 và password:123
- Cấp các tài khoản có vai trò là quản lý
-	Đăng nhập tài khoản mới và xóa tài khoản admin
-	Xem hướng dẫn sử dụng trong menu trợ giúp của phần mềm
<a name="DongGoiVaTrienKhai"></a>
## V ĐÓNG GÓI VÀ TRIỂN KHAI

<a name="SanPhamPhanMem"></a>
### 5.1	SẢN PHẨM PHẦN MỀM

| TT | Thành phần  | Mô tả |
|--------------|-------|------|
| 1 | RestaurantVictory-setup.exe | File cài đặt |
| 2 | DB_QuanLyNhaHang | Cơ sở dữ liệu |

<a name="HuongDanCaiDat"></a>
### 5.2	HƯỚNG DẪN CÀI ĐẶT

-	Bước 1: Chạy DB_QuanLyNhaHang.sql để tạo CSDL QuanLyNhaHang
-	Bước 2: Đặt mật khẩu sa của SQL Server là 123456
-	Bước 3: Chạy file Vuabienrestaurant-setup.exe (chú ý môi trường JDK1.8+)
-	Bước 4: Chạy ứng dụng NVQL và đăng nhập với tài khoản (TK: NV01 ; MK: 123) 

<a name="TongKet"></a>
## VI. Tổng kết
- Tự đánh giá việc triển khai bài tập nhóm, tự nhận xét kết quả đạt được:

  - Nhóm đã hoàn thành được hầu hết mọi tính năng chính đã đặt ra từ đầu và bổ sung thêm các tính năng mới.

  - Thành viên trong nhóm khá hài lòng với sản phẩm của nhóm xây dựng (mặc dù còn một số phần chưa hài lòng VD: tốc độ load bị ảnh hưởng do ảnh, giao diện hơi đơn giản, còn lỗi khi chạy ...).

  - Công việc trong nhóm nhiều lúc còn bị chậm. Việc liên lạc và phối hợp giữa các thành viên nhiều lúc còn không chặt chẽ.
- Nêu bài học kinh nghiệm rút ra từ bài tập dự án của nhóm:
  - Học được về lập trình android Java biết thêm về nhưng thư viện hay.
  - Học được thêm về làm việc theo nhóm, sử dụng các công cụ hỗ trợ (GitHub, GitBook...) để hoàn thành 1 dự án.

