# PlacesAroundMe
- Xử lí tối ưu:
	+ Mỗi lần tải tối đa 20 địa điểm, khi nào người dùng trượt tới cuối danh sách, ứng dụng sẽ tải thêm tối đa 20 địa điểm nữa.
	+ Ảnh chỉ được load lên khi người dùng trượt tới item đó, và ảnh được lưu trong internal storage tránh tải lại ở các lượt tiếp theo.
	+ Các ảnh của các item trong danh sách địa điểm kích cỡ width=100px, chỉ khi nào click vào detail mới tải ảnh kích cỡ 400px
	+ Chỉ khi nào click vào item, sẽ request lấy thông tin detail của địa điểm đó.
	
- Phương pháp test
	+ Dùng Genymotion và Ellipse Logcat để xem log của ứng dụng
		Khi trượt tới cuối item, ứng dụng sẽ request thêm tối đa 20 địa điểm khác nữa.
		Chỉ khi nào item xuất hiện thì ảnh mới được load lên.
		Các ảnh được cache trong intenal storage sẽ không cần download nữa, chỉ cần load from internal storage
	+ Bật tắt mạng liên tục, ứng dụng vẫn hoạt động, không bị treo.
	+ Dùng genymotion thiết lập vị trí GPS để test các địa điểm mà ứng dụng đã tải, cùng các thông tin kèm theo nếu có.
	
- Test các trường hợp:
	+ Tắt mạng, bật ứng dụng lên -> Ứng dụng chạy bình thường không crash, không có dữ liệu được load -> OK
	+ Bật mạng tải các địa điểm -> Tắt mạng -> Trượt xuống tới cuối cùng (Không load, không bị crash) -> Bật mạng ứng dụng load thêm các địa điểm khác -> OK
	+ Bật mạng tải các địa điểm -> Tắt mạng -> Click vào 1 địa điểm để xem chi tiết -> Ứng dụng hiển thị hình ảnh và các thông tin sơ bộ lấy được từ PlaceSearch, nếu có mạng, ứng dụng sẽ hiển thị thêm các thông tin khác như Formatted_address, Phone, International Phone,... -> OK không bị crash.
	+ Tắt GPS, bật ứng dụng -> Ứng dụng alert Yêu cầu bật GPS, sau khi bật GPS xong, ứng dụng sẽ load danh sách các địa điểm. -> OK
	+ Load danh sách các địa điểm, ban đầu load hình ảnh chậm do phải tải từ server. Tắt ứng dụng, bật lại ứng dụng, hình ảnh đã được load nhanh chóng (Lưu trong internal storage) -> OK

- Chức năng đã cài đặt
	+ Load danh sách các địa điểm xung quanh mình: dùng Location Service định vị trí, dùng Google Places Search Service để lấy thông tin các vị trí xung quanh địa điểm đó.
	+ Cho phép lấy thông tin chi tiết của một địa điểm: Dùng Google Place Details Service
	+ Lấy ảnh đại diện của một địa điểm: Dùng Google Place Photos Service
	+ GPS Settings Alert material design
	+ Cache image
	
VNG Fresher Assignment
