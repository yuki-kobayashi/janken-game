# OpenJDK 17ベースイメージ
FROM eclipse-temurin:17-jdk

# 作業ディレクトリを設定
WORKDIR /app

# jarファイルと必要ファイルをコピー
COPY . /app

# jarファイルをビルド
RUN ./mvnw clean package -DskipTests

# ポート番号
EXPOSE 8080

# アプリを起動
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]