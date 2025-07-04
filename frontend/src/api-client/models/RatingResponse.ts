/* tslint:disable */
/* eslint-disable */
/**
 * Croqueteria - API Documentation
 * Endpoints for Croqueteria API, a service that allows to describe, rate and discuss the most delicious croquettes around the globe.
 *
 * The version of the OpenAPI document: v1
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface RatingResponse
 */
export interface RatingResponse {
    /**
     * Unique identifier of the rating
     * @type {number}
     * @memberof RatingResponse
     */
    id?: number;
    /**
     * Identifier of the croquette being rated
     * @type {number}
     * @memberof RatingResponse
     */
    croquetteId?: number;
    /**
     * Identifier of the user who submitted the rating
     * @type {number}
     * @memberof RatingResponse
     */
    userId?: number;
    /**
     * Rating value given by the user (e.g., 1 to 5)
     * @type {number}
     * @memberof RatingResponse
     */
    rating: number;
}

/**
 * Check if a given object implements the RatingResponse interface.
 */
export function instanceOfRatingResponse(value: object): value is RatingResponse {
    if (!('rating' in value) || value['rating'] === undefined) return false;
    return true;
}

export function RatingResponseFromJSON(json: any): RatingResponse {
    return RatingResponseFromJSONTyped(json, false);
}

export function RatingResponseFromJSONTyped(json: any, ignoreDiscriminator: boolean): RatingResponse {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'] == null ? undefined : json['id'],
        'croquetteId': json['croquetteId'] == null ? undefined : json['croquetteId'],
        'userId': json['userId'] == null ? undefined : json['userId'],
        'rating': json['rating'],
    };
}

export function RatingResponseToJSON(json: any): RatingResponse {
    return RatingResponseToJSONTyped(json, false);
}

export function RatingResponseToJSONTyped(value?: RatingResponse | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'croquetteId': value['croquetteId'],
        'userId': value['userId'],
        'rating': value['rating'],
    };
}

