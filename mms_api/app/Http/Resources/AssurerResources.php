<?php

namespace App\Http\Resources;
use App\Http\Resources\UsersResource;

use Illuminate\Http\Resources\Json\JsonResource;

class AssurerResources extends JsonResource
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
            // 'profession' => $this->profession,
            // 'employeur' => $this->employeur,
            // 'etat' => $this->etat,
            'user' => new UsersResource($this),
        ];
    }
}

