<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePortefeuillesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('portefeuilles', function (Blueprint $table) {
            $table->bigIncrements('id');
            $table->string('montant')->default(0);
            $table->unsignedBigInteger('contrat_id'); 
            $table->foreign('contrat_id')->references('id')->on('contrats')->ondelete('cascade');
            
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('portefeuilles');
    }
}
