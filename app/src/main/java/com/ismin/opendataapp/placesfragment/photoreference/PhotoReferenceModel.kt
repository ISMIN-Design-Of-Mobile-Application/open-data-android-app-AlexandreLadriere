package com.ismin.opendataapp.placesfragment.photoreference

object PhotoReferenceModel {
    data class Result(val candidates: Array<Candidates>)
    data class Candidates(val photos: Array<Photos>)
    data class Photos(val photo_reference: String)
}