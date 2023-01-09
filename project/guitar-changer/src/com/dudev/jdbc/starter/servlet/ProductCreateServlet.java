package com.dudev.jdbc.starter.servlet;

import com.dudev.jdbc.starter.dto.GuitarDto;
import com.dudev.jdbc.starter.dto.UserDto;
import com.dudev.jdbc.starter.services.BrandService;
import com.dudev.jdbc.starter.services.CategoryService;
import com.dudev.jdbc.starter.services.ChangeTypeService;
import com.dudev.jdbc.starter.services.ProductService;
import com.dudev.jdbc.starter.util.JSPHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet("/product-create")
public class ProductCreateServlet extends HttpServlet {

    private final CategoryService categoryService = CategoryService.getInstance();
    private final BrandService brandService = BrandService.getInstance();
    private final ChangeTypeService changeTypeService = ChangeTypeService.getInstance();
    private final ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String category = req.getParameter("category");
        if (category != null) {
            req.getServletContext().setAttribute("category", categoryService.getCategoryById(Integer.parseInt(category)));
            req.setAttribute("category", categoryService.getCategoryById(Integer.parseInt(category)));
            req.setAttribute("brands", brandService.getBrandsByCategory(Integer.parseInt(category)));
            req.setAttribute("changeTypes", changeTypeService.getChangeTypes());

        }
        req.setAttribute("categories", categoryService.getAllCategories());
        req.getRequestDispatcher(JSPHelper.getPath("/product-create"))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part image = req.getPart("image");
        if (Objects.equals(req.getParameter("product"), "guitar")) {
            productService.createProduct(GuitarDto.builder()
                    .brand(req.getParameter("brand"))
                    .changeValue((req.getParameter("changeValue") != null && !req.getParameter("changeValue").isEmpty()) ? Double.parseDouble(req.getParameter("changeValue")) : 0)
                    .country(req.getParameter("country"))
                    .model(req.getParameter("model"))
                    .changeWish(req.getParameter("changeWish"))
                    .price(Double.parseDouble(req.getParameter("price")))
                    .description(req.getParameter("description"))
                    .media(image.getSubmittedFileName())
                    .userId(UUID.fromString(((UserDto) req.getSession().getAttribute("user")).getId()))
                    .wood(req.getParameter("wood"))
                    .year(req.getParameter("year") != null?Integer.parseInt(req.getParameter("year")):0)
                    .pickUps(req.getParameter("pickUps"))
                    .changeType(req.getParameter("changeType"))
                    .build(), image);
        }
        req.getRequestDispatcher(JSPHelper.getPath("/account"))
                .forward(req, resp);
    }
}
