<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

class SuperMarchandRequest extends FormRequest
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
            'matricule '  => ['required', 'string'],
            'commission '  => ['required', 'integer'],
        ];
    }
}
