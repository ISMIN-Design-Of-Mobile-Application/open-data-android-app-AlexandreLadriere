package com.ismin.opendataapp.placesfragment

object PlaceModel {
    data class Result(val count: Int, val data: Data)
    data class Data(val features: Array<Features>)
    data class Features(val properties: Properties, val geometry: Geometry)
    data class Properties(val name: String, val proximity: Double, val contact_details: ContactDetails, val address_components: AddressComponents, val photo_reference: String)
    data class Geometry(val coordinates: Array<Double>)
    data class ContactDetails(val website: String)
    data class AddressComponents(val address: String, val postal_code: String, val city: String, val province: String, val country: String)
}