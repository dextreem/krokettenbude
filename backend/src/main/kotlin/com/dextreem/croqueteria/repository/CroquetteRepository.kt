package com.dextreem.croqueteria.repository

import com.dextreem.croqueteria.entity.Croquette
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CroquetteRepository : CrudRepository<Croquette, Int>, JpaSpecificationExecutor<Croquette> {
}