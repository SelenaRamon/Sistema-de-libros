package org.esfe.controladores;

import jakarta.validation.Valid;
import org.esfe.modelos.Autor;
import org.esfe.modelos.Categoria;
import org.esfe.modelos.Editorial;
import org.esfe.modelos.Libro;
import org.esfe.servicios.interfaces.IAutorService;
import org.esfe.servicios.interfaces.ICategoriaService;
import org.esfe.servicios.interfaces.IEditorialService;
import org.esfe.servicios.interfaces.ILibroService;
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
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private ILibroService libroService;

    @Autowired
    private IAutorService autorService;

    @Autowired
    private IEditorialService editorialService;

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public String index(Model model,
                        @RequestParam("page") Optional<Integer> page,
                        @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(5);
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Libro> libros = libroService.buscarTodosPaginados(pageable);

        model.addAttribute("libros", libros);

        int totalPages = libros.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "libro/index";
    }

    @GetMapping("/create")
    public String create(Libro libro, Model model) {
        model.addAttribute("autores", autorService.obtenerTodos());
        model.addAttribute("editoriales", editorialService.obtenerTodos());
        model.addAttribute("categorias", categoriaService.obtenerTodos());
        return "libro/create";
    }

    @PostMapping("/save")
    public String save( Libro libro, @RequestParam("autor") Integer autor,
                       @RequestParam("editorial") Integer editorial,  @RequestParam("categoria") Integer categoria,
                       BindingResult result,
                       Model model,
                       RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute("libro", libro);
            return "libro/create";
        }

        Autor autorobj=new Autor();
        autorobj.setId(autor);

        Editorial editorials=new Editorial();
        editorials.setId(editorial);

        Categoria categoria1=new Categoria();
        categoria1.setId(categoria);


        libro.setAutor(autorobj);
        libro.setEditorial(editorials);
        libro.setCategoria(categoria1);

        libroService.crearOEditar(libro);
        attributes.addFlashAttribute("msg", "Libro creado correctamente");
        return "redirect:/libros";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Optional<Libro> optionalLibro = libroService.buscarPorId(id);
        if (optionalLibro.isPresent()) {
            model.addAttribute("libro", optionalLibro.get());
            return "libro/details";
        } else {
            return "redirect:/libros";
        }
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Optional<Libro> optionalLibro = libroService.buscarPorId(id);
        if (optionalLibro.isPresent()) {
            model.addAttribute("libro", optionalLibro.get());
            return "libro/edit";
        } else {
            return "redirect:/libros";
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Integer id, Model model) {
        Optional<Libro> optionalLibro = libroService.buscarPorId(id);
        if (optionalLibro.isPresent()) {
            model.addAttribute("libro", optionalLibro.get());
            return "libro/delete";
        } else {
            return "redirect:/libros";
        }
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("libro") Libro libro, RedirectAttributes attributes) {
        libroService.eliminarPorId(libro.getId());
        attributes.addFlashAttribute("msg", "Libro eliminado correctamente");
        return "redirect:/libros";
    }
}
