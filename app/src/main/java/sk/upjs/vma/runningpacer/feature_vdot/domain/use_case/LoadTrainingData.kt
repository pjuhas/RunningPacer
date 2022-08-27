package sk.upjs.vma.runningpacer.feature_vdot.domain.use_case

import android.content.Context
import android.content.res.AssetManager
import org.json.JSONObject
import sk.upjs.vma.runningpacer.feature_vdot.domain.model.*

class LoadTrainingData {
    operator fun invoke(context: Context): TrainingTimesList {
        val listOfRaceTimes = TrainingTimesList()

        val parsedRaceObject = JSONObject(context.assets.readAssetsFile("training_paces.json"))
        val parsedRaceArray = parsedRaceObject.getJSONArray("pace")

        for (i in 0 until parsedRaceArray.length()) {
            listOfRaceTimes.vdotTypeList.add(
                VdotType(
                    vdot = parsedRaceArray.getJSONObject(i).getInt("VDOT")
                )
            )
            listOfRaceTimes.easyTypeList.add(
                EasyType(
                    distanceKm = parsedRaceArray.getJSONObject(i).getString("Easy Km")
                )
            )
            listOfRaceTimes.marathonTypeList.add(
                MarathonType(
                    distanceKm = parsedRaceArray.getJSONObject(i).getInt("M Km")
                )
            )
            listOfRaceTimes.thresholdTypeList.add(
                ThresholdType(
                    distanceKm = parsedRaceArray.getJSONObject(i).getInt("T Km"),
                    distanceFourm = parsedRaceArray.getJSONObject(i).getInt("T 400m")
                )
            )
            listOfRaceTimes.intervalTypeList.add(
                IntervalType(
                    distanceFourm = parsedRaceArray.getJSONObject(i).getInt("I 400m"),
                    distanceKm = parsedRaceArray.getJSONObject(i).getInt("I Km"),
                    distanceMile = parsedRaceArray.getJSONObject(i).getInt("I Mile"),
                    distanceOnetwom = parsedRaceArray.getJSONObject(i).getInt("I 1200m"),
                )
            )
            listOfRaceTimes.relayTypeList.add(
                RelayType(
                    distanceTwom = parsedRaceArray.getJSONObject(i).getInt("R 200m"),
                    distanceThreem = parsedRaceArray.getJSONObject(i).getInt("R 300m"),
                    distanceFourm = parsedRaceArray.getJSONObject(i).getInt("R 400m"),
                    distanceSixm = parsedRaceArray.getJSONObject(i).getInt("R 600m"),
                    distanceEightm = parsedRaceArray.getJSONObject(i).getInt("R 800m")
                )
            )
        }
        return listOfRaceTimes
    }

    private fun AssetManager.readAssetsFile(fileName: String): String =
        open(fileName).bufferedReader().use { it.readText() }

}
