# PlacesAroundMe
- Xử lí tối ưu:
	+ Mỗi lần tải tối đa 20 địa điểm, khi nào người dùng trượt tới cuối danh sách, ứng dụng sẽ tải thêm tối đa 20 địa điểm nữa.
	+ Ảnh chỉ được load lên khi người dùng trượt tới item đó, và ảnh được lưu trong cache để tránh request tới server lần sau.
	+ Chỉ khi nào click vào item, sẽ request lấy thông tin detail của địa điểm đó.
- Phương pháp test
	+ Dùng Genymotion và Ellipse Logcat để xem log của ứng dụng, khi trượt tới cuối danh sách, ứng dụng tự tải thêm 20 dòng nữa.
	+ Khi xem tới item nào thì ảnh mới được load lên. Những ảnh lưu trong cache không được request tới server nữa.
	+ Bật tắt mạng liên tục, ứng dụng vẫn hoạt động, không bị treo.
	+ Dùng genymotion thiết lập vị trí GPS để test các địa điểm mà ứng dụng đã tải, cùng các thông tin kèm theo nếu có.

VNG Fresher Assignment
