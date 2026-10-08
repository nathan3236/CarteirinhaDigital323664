package com.senai.carteirinhadigital.feature.unidadecurriculares.domain.repository

import com.senai.carteirinhadigital.feature.unidadecurriculares.domain.model.UnidadeCurricular

interface UnidadeCurricularRepository {
    suspend fun listar():Result<List<UnidadeCurricular>>
}