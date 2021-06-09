package pwr.aiir.SubTaskResults

import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.constraints.NotNull

@Singleton
class SubTaskResultsRepository(private val entityManager: EntityManager) {

    @Transactional
    fun save(@NotNull subTaskResult: SubTaskResult): SubTaskResult {
        entityManager.persist(subTaskResult)
        return subTaskResult;
    }
}