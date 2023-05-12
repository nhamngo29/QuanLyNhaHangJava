# QUẢN LÝ NHÀ HÀNG
<p>Language:Java</p>
<p>Database:SQL Saver</p>
<hr/>
<p>Member<p> 
<ul>
  <li>Nguyễn Nhâm Ngọ (https://github.com/nhamngo29)</li>
  <li>Trần Quang Khải</li>
  <li>Nguyễn Cường</li>
</ul>

### Mục lục
[I. PHÂN TÍCH](#Modau)
[III. Tổng kết](#TongKet)
<a name="Modau"></a>
## I. PHÂN TÍCH
### 1.1	HIỆN TRẠNG-KHẢO SÁT
Phần mềm quản lý nhà hàng, quán ăn, quán cafe...`Restaurant Victory` cung cấp giải pháp quản lý nhà hàng toàn diện, triển khai dễ dàng, nhanh chóng với nhiều tính năng ưu việt:
-	Đáp ứng tốt theo mọi hình thức: Gọi món phục vụ tại bàn, tự chọn (buffet, gọi món), phục vụ nhanh.
- Đáp ứng nghiệp vụ cho: Thu ngân, nhân viên phục vụ và quản lý nhà hàng.
- Hỗ trợ các nghiệp vụ quản lý tồn kho, chi phí và lương nhân viên.
- Chạy tốt trên thiết bị: Máy tính
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
#### Yêu cầu về môi trường công nghệ
      Ứng dụng phải được thực với công nghệ Swing và JDBC chạy trên mọi hệ điều hành với môi trường JDK tối thiểu 1.8
      Hệ quản trị CSDL SQL Server 2008 trở lên
### 1.3	USE CASE
  Use case là sơ đồ tổng quan về mặt chức năng và phân vai trò người sử dụng. Dựa vào yêu cầu hệ thống của khách hàng, chúng ta có thể phác thảo sơ đồ use case như sau.
  ![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/4814ab30-47de-4fda-a47f-b4edd3120faf)
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
## II. THIẾT KẾ
### 2.1	MÔ HÌNH TRIỂN KHAI
Ứng dụng phần mềm được xây dựng để phục vụ cho nhiều người dùng nhưng cơ sở dữ liệu thì lưu trữ tập trung.
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/4d370b01-7abc-4c44-88a7-9c8d1b0fb34e)
- Cần một máy cài SQL Server 2008+. Máy này cần hệ điều hành window xp trở lên
-	Các máy nhân viên phòng đào tạo cài phần mềm edusys. Các máy nhân viên cần JDK 1.8+ với hệ điều hành bất ký
### 2.2	THIẾT KẾ CSDL
#### 2.2.1	Sơ đồ quan hệ thực thể
##### *2.2.1.1	ERD Diagram level 1*
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/6185eaf8-459c-40a5-a461-7eb00f115a87)
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/ace257a6-5770-4972-b2b6-3546837425d3)
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

### 2.3	THIẾT KẾ GIAO DIỆN (MOCKUP)
#### 2.3.1	Sơ đồ tổ chức giao diện
Giao diện chính là một cửa sổ chứa menu chính và toolbar. Thông qua đó để đi đến các giao diện thành viên để thực hiện các chức năng trong hệ thống.
Theo yêu cầu thì mọi nhân viên phải đăng nhập trước khi sử dụng ứng dụng nên form đăng nhập xuất hiện trước để yêu cầu đăng nhập.
Ngoài ra mỗi ứng dụng trong thời gian khởi động cần có một màn hình chào cùng với thanh tiến trình để người có cảm giác ứng dụng đang khởi động.
![image](https://github.com/nhamngo29/QuanLyNhaHangJava/assets/107678223/44b39c9f-426c-4d60-860a-bda666805e35)
### 2.3.2	Giao diện mockup:
- Login
- Menu và bàn
- Quản lý doanh thu
- Quản lý bàn
- Thông tin tài khoản
- Đổi mật khẩu
## III	KIỂM THỬ
## III. Tổng kết
<a name="TongKet"></a>
- Tự đánh giá việc triển khai bài tập nhóm, tự nhận xét kết quả đạt được:

  - Nhóm đã hoàn thành được hầu hết mọi tính năng chính đã đặt ra từ đầu và bổ sung thêm các tính năng mới.

  - Thành viên trong nhóm khá hài lòng với sản phẩm của nhóm xây dựng (mặc dù còn một số phần chưa hài lòng VD: tốc độ load bị ảnh hưởng do ảnh, giao diện hơi đơn giản, còn lỗi khi chạy ...).

  - Công việc trong nhóm nhiều lúc còn bị chậm. Việc liên lạc và phối hợp giữa các thành viên nhiều lúc còn không chặt chẽ.
- Nêu bài học kinh nghiệm rút ra từ bài tập dự án của nhóm:

  - Học được về lập trình android Java biết thêm về nhưng thư viện hay.
  - Học được thêm về làm việc theo nhóm, sử dụng các công cụ hỗ trợ (GitHub, GitBook...) để hoàn thành 1 dự án.

