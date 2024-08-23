package org.esfe.servicios.interfaces;

import org.esfe.modelos.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEditorialService {
    Page<Editorial> buscarTodosPaginados(Pageable pageable);

    List<Editorial> obtenerTodos();

    Optional<Editorial> buscarPorId(Integer id);

    Editorial crearOEditar(Editorial editorial);

    void eliminarPorId(Integer id);
}
