<?php

namespace App\Http\Requests;

use Illuminate\Foundation\Http\FormRequest;

use Illuminate\Http\Request;
class PlanteRequest extends FormRequest
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
            'nom' => 'required|string|max:255|unique:plantes' ,
            'surnom' => 'sometimes|string|max:255|unique:plantes' ,
            'description' => 'required',
            'conseil' => 'sometimes',
        ];
    }
}
