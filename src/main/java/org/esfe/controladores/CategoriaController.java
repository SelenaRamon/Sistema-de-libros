package org.esfe.controladores;

import jakarta.validation.Valid;
import org.esfe.modelos.Categoria;
import org.esfe.modelos.Editorial;
import org.esfe.servicios.interfaces.ICategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Categoria> categorias = categoriaService.buscarTodosPaginados(pageable);
        model.addAttribute("categorias", categorias);
        int totalPages = categorias.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "categoria/index";
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categoria/create";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute("categoria") @Valid Categoria categoria, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("categoria", categoria);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a errores.");
            return "categoria/create";
        }
        categoriaService.crearOEditar(categoria);
        attributes.addFlashAttribute("msg", "Editorial creado correctamente");
        return "redirect:/categorias";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model){
        Categoria categoria = categoriaService.buscarPorId(id).get();
        model.addAttribute ("categoria", categoria);
        return "categoria/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id).get();
        model.addAttribute("categoria", categoria);
        return "categoria/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Categoria categoria = categoriaService.buscarPorId(id).get();
        model.addAttribute("categoria", categoria);
        return "categoria/delete";
    }

    @PostMapping("/delete")
    public String delete(Categoria categoria, RedirectAttributes attributes) {
       categoriaService.eliminarPorId(categoria.getId());
        attributes.addFlashAttribute("msg", "Categoria eliminada correctamente");
        return "redirect:/categorias";
    }
}
