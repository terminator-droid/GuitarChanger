package com.dudev.jdbc.starter;

import com.dudev.jdbc.starter.util.ConnectionManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BlobRunner {
    public static void main(String[] args) {
        addImage();
    }
    private static void addImage() {
        try (Connection connection = ConnectionManager.getConnection()) {
            var sql = """
                    UPDATE jdbc_tester SET photo = ?
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setBytes(1, Files.readAllBytes(Path.of("resources", "guitar.jpg")));
            preparedStatement.executeUpdate();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
