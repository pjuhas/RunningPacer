package sk.upjs.vma.runningpacer.feature_vdot.data.repository

import sk.upjs.vma.runningpacer.feature_vdot.domain.model.TableData

interface DataRepository {

    suspend fun setRaceDistances(distance: TableData)

}