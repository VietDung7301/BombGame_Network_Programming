## Cách chạy project
# 1. Khởi động server
cd server
make
./server <server_port>
# 2. Khởi động client
cd client/transport
make
./transport <server_address> <server_port>
---
