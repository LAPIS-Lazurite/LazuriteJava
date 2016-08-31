# コンパイラを指定
CC :=g++
# インクルードファイル等
CFLAGS := -fPIC -shared
INCLUDE :=-I /usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/include -I  /usr/lib/jvm/jdk-8-oracle-arm32-vfp-hflt/include/linux/ 

# buildするファイル
CFILES =  liblazdriver.c

# 出力するライブラリの名前
OUTPUT := $(CFILES:.c=.so)
all: $(TARGET)
	$(CC) $(CFLAGS) $(CFILES) -o $(OUTPUT)  $(INCLUDE)

clean:
	rm -rf $(OUTPUT)
