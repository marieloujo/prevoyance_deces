<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class AssuranceRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'garantie'  => ['required', 'integer'],
            'prime'     => ['required', 'integer'],
            'duree'  => ['required', 'integer','min:1'],
            'date_debut'  => ['required', 'string','after_or_equal:now'],
            'date_echeance'  => ['required', 'string'],
            'date_effet'  => ['required', 'string'],
        ];
    }
}
