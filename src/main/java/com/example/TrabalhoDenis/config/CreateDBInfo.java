package com.example.TrabalhoDenis.config;

import com.example.TrabalhoDenis.model.Category;
import com.example.TrabalhoDenis.model.Product;
import com.example.TrabalhoDenis.model.User;
import com.example.TrabalhoDenis.repository.CategoryRepository;
import com.example.TrabalhoDenis.repository.ProductRepository;
import com.example.TrabalhoDenis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CreateDBInfo implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args)
    {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {

            User user = new User();
            user.setName("Admin");
            user.setEmail("admin@gmail.com");
            user.setPassword("admin");
            user.setRole("ADMIN");
            user.setActive(true);

            userRepository.save(user);

            System.out.println("Criou usuario");
        }

        if (categoryRepository.count() == 0) {

            Category brinquedos = new Category();
            brinquedos.setName("Brinquedos");

            Category eletronicos = new Category();
            eletronicos.setName("Eletrônicos");

            categoryRepository.save(brinquedos);
            categoryRepository.save(eletronicos);

            Product p1 = new Product();
            p1.setName("Produto super hiper interessante 1");
            p1.setDescription("Super hiper mega extra interessante");
            p1.setPrice(6767.0);
            p1.setStockQuantity(67);
            p1.setCategory(brinquedos);

            Product p2 = new Product();
            p2.setName("Homem aranha Aliexpress");
            p2.setDescription("Made in China");
            p2.setPrice(2.50);
            p2.setStockQuantity(2000);
            p2.setCategory(brinquedos);

            Product p3 = new Product();
            p3.setName("Fone Bluetooth");
            p3.setDescription("Funcionando apenas de um lado");
            p3.setPrice(150.0);
            p3.setStockQuantity(15);
            p3.setCategory(eletronicos);

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
        }
    }
}
