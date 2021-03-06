package com.ghstudios.android.data.classes

import android.util.Log
import com.ghstudios.android.ITintedIcon
import com.ghstudios.android.mhgendatabase.R

import java.util.ArrayList
import java.util.HashMap

private class DummyWeapon(val slots: Int): Equipment(), ITintedIcon {
    override fun getNumSlots() = slots

    override fun getIconResourceString() = "icon_great_sword"
    override fun getColorArrayId() = R.array.rare_colors
    override fun getIconColorIndex() = 0
}

/**
 * Contains all of the juicy stuff regarding ASB sets, like the armor inside and the skills it provides.
 */
class ASBSession: ArmorSet {
    private var asbSet: ASBSet? = null

    private val pieceData = sortedMapOf<Int, ArmorSetPiece>()

    val id: Long
        get() = asbSet!!.id

    val name: String
        get() = asbSet?.name ?: ""

    val rank: Int
        get() = asbSet!!.rank

    val hunterType: Int
        get() = asbSet!!.hunterType


    var numWeaponSlots: Int
        /** Retrieves the number of weapon slots */
        get() = getEquipment(ArmorSet.WEAPON)?.numSlots ?: 0

        /**
         * Sets the number of weapon slots. This also clears all decorations.
         */
        set(value) {
            setEquipment(ArmorSet.WEAPON, DummyWeapon(value))
        }

    init {
        setEquipment(ArmorSet.WEAPON, DummyWeapon(3))
    }

    override val pieces: List<ArmorSetPiece> get() = pieceData.values.toList()

    override fun getPiece(pieceIndex: Int) = pieceData[pieceIndex]

    /**
     * @return The set's talisman.
     */
    val talisman: ASBTalisman?
        get() = pieceData[ArmorSet.TALISMAN]?.equipment as ASBTalisman?

    fun setASBSet(set: ASBSet) {
        asbSet = set
    }

    fun getDecorations(pieceIndex: Int): List<Decoration> {
        return getPiece(pieceIndex)?.decorations ?: emptyList()
    }

    fun getAvailableSlots(pieceIndex: Int): Int {
        val equipment = getEquipment(pieceIndex) ?: return 0
        val usedSlots = getDecorations(pieceIndex).sumBy { it.numSlots }
        return equipment.numSlots - usedSlots
    }

    /**
     * Attempts to add a decoration to the specified armor piece.
     * @param pieceIndex   The index of a piece in the set to fetch, according to [ASBSession].
     * @param decoration   The decoration to add.
     * @return The 0-based index of the slot that the decoration was added to, or -1 if it failed
     */
    fun addDecoration(pieceIndex: Int, decoration: Decoration): Int {
        val piece = getPiece(pieceIndex) ?: return -1

        Log.v("ASB", "Adding decoration at piece index $pieceIndex")
        if (getAvailableSlots(pieceIndex) >= decoration.numSlots) {
            piece.decorations.add(decoration)
            return piece.decorations.size - 1
        } else {
            Log.e("ASB", "Cannot add that decoration!")
            return -1
        }
    }

    /**
     * Removes the decoration at the specified location from the specified armor piece.
     * Will fail if the decoration in question is non-existent or a dummy.
     */
    fun removeDecoration(pieceIndex: Int, decorationIndex: Int) {
        val list = getPiece(pieceIndex)?.decorations ?: return
        if (list.getOrNull(decorationIndex) == null) {
            return
        }

        list.removeAt(decorationIndex)
    }

    /**
     * @return A piece of the armor set based on the provided piece index.
     * Returns null if there is no equipment in that slot.
     */
    fun getEquipment(pieceIndex: Int): Equipment? {
        return getPiece(pieceIndex)?.equipment
    }

    /**
     * Changes the equipment at the specified location.
     */
    fun setEquipment(pieceIndex: Int, equip: Equipment) {
        pieceData[pieceIndex] = ArmorSetPiece(pieceIndex, equip)
    }

    /**
     * Removes the equipment at the specified location.
     * If a weapon, clears the decorations.
     */
    fun removeEquipment(pieceIndex: Int) {
        if (pieceIndex == ArmorSet.WEAPON) {
            getPiece(pieceIndex)?.decorations?.clear()
        } else {
            pieceData.remove(pieceIndex)
        }
    }
}
