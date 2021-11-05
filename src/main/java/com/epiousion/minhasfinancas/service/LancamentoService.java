package com.epiousion.minhasfinancas.service;

import com.epiousion.minhasfinancas.model.entity.Lancamento;
import com.epiousion.minhasfinancas.model.enums.StatusLancamento;

import java.util.List;

public interface LancamentoService {

    Lancamento salvar(Lancamento lancamento);

    Lancamento atualizar(Lancamento lancamento);

    void deletar(Lancamento lancamento);

    List<Lancamento> buscar(Lancamento lancamentoFiltro);

    void autalizarStatus(Lancamento lancamento, StatusLancamento status);

    void validar(Lancamento lancamento);
}
