package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.*;
import com.example.MATRICULA.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final CuotaRepository    cuotaRepo;
    private final ReciboRepository   reciboRepo;
    private final MatriculaService   matriculaService;

    public List<Cuota> listarDeudas(Integer idAlumno, Integer idAnio) {
        Matricula matricula = matriculaService
                .obtenerPorAlumnoYAnio(idAlumno, idAnio);
        return cuotaRepo.findByMatriculaIdMatriculaOrderByConceptoOrden(
                matricula.getIdMatricula());
    }

    @Transactional(rollbackFor = Exception.class)
    public Recibo pagar(Integer idAlumno, Integer idAnio,
                        Integer idCuota, String usuario) {
        try {
            // 1. Obtener cuota
            Cuota cuota = cuotaRepo.findById(idCuota)
                    .orElseThrow(() -> new RuntimeException("Cuota no encontrada"));

            // 2. Validar que pertenece a la matrícula del alumno
            Matricula matricula = matriculaService
                    .obtenerPorAlumnoYAnio(idAlumno, idAnio);
            if (!cuota.getMatricula().getIdMatricula()
                    .equals(matricula.getIdMatricula()))
                throw new RuntimeException(
                        "La cuota no pertenece a la matrícula de este alumno");

            // 3. Validar que no esté ya pagada
            if (cuota.getEstadoPago() == 'C')
                throw new RuntimeException("Esta cuota ya fue pagada anteriormente");

            // 4. Validar orden — no puede pagar si hay deuda anterior
            boolean hayDeudaAnterior = cuotaRepo.existeDeudaAnterior(
                    matricula.getIdMatricula(),
                    cuota.getConcepto().getOrden());
            if (hayDeudaAnterior)
                throw new RuntimeException(
                        "Existen cuotas anteriores pendientes de pago. " +
                                "Debe cancelarlas primero.");

            // 5. Marcar cuota como pagada
            cuota.setEstadoPago('C');
            cuota.setUsuUpdate(usuario);
            cuotaRepo.save(cuota);

            // 6. Generar número de boleta correlativo
            String nroBoleta = generarNroBoleta();

            // 7. Crear recibo
            Recibo recibo = Recibo.builder()
                    .cuota(cuota)
                    .nroBoleta(nroBoleta)
                    .montoPagado(cuota.getMonto())
                    .estado('A')
                    .usuInsert(usuario)
                    .build();

            return reciboRepo.save(recibo);

        } catch (RuntimeException e) {
            throw e;
        }
    }

    private String generarNroBoleta() {
        Integer ultimo = reciboRepo.obtenerUltimoId();
        int siguiente  = (ultimo == null ? 0 : ultimo) + 1;
        return String.format("BOL-%06d", siguiente);
    }
}