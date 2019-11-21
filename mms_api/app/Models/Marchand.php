<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Marchand extends Model
{

    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'matricule','user_id','commission','crédit_virtuel','super_marchand_id',
    ];

    public function user(){
        return $this->belongsTo('App\User','user_id');
    }

    public function super_marchand(){
        return $this->belongsTo('App\Models\SuperMarchand','super_marchand_id');
    }

    public function clients(){
        return $this->hasMany('App\Models\Client');
    }
    
    public function comptes(){
        return $this->morphMany('App\Models\Compte','compteable');
    }
}
