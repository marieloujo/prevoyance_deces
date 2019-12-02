<?php

namespace App\Http\Resources;
use App\Http\Resources\UserResource;
use Illuminate\Http\Resources\Json\JsonResource;

class SuperMarchandResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request)
    {
        return [

            'id' => $this->id,
            'matricule' => $this->matricule,
            'commission' => $this->commission,
            'user' => new UserResource($this->user),
        ];
    }
}
