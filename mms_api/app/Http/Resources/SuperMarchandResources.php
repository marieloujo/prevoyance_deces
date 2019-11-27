<?php

namespace App\Http\Resources;

use App\Http\Resources\UsersResource;
use Illuminate\Http\Resources\Json\JsonResource;

class SuperMarchandResources extends JsonResource
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
            // 'id' => $this->id,
            // 'matricule' => $this->matricule,
            // 'commission' => $this->commission,
            'user' => new UsersResource( $this->user),
        ];
    }
}


